package cn.com.bestpay.portal.mojo;


import cn.com.bestpay.portal.config.property.SystemProperty;
import com.google.common.io.CharStreams;
import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.yahoo.platform.yui.compressor.CssCompressor;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @goal compress
 * @phase process-resources
 *
 * @threadSafe
 */
public class CompressorMojo extends MojoSupport {

    /**
     * Read the input file using "encoding".
     *
     * @parameter expression="${file.encoding}" default-value="UTF-8"
     */
    private String encoding;

    /**
     * The output filename suffix.
     *
     * @parameter expression="${maven.yuicompressor.suffix}" default-value="-min"
     */
    private String suffix;


    /**
     * Read the input file using "encoding".
     *
     * @parameter expression="${file.systemProperty}" default-value=""
     */
    private String systemProperty;

    /**
     * If no "suffix" must be add to output filename (maven's configuration manage empty suffix like default).
     *
     * @parameter expression="${maven.yuicompressor.nosuffix}" default-value="false"
     */
    private boolean nosuffix;

    /**
     * Insert line breaks in output after the specified column number.
     *
     * @parameter expression="${maven.yuicompressor.linebreakpos}" default-value="-1"
     */
    private int linebreakpos;

    /**
     * [js only] No compression
     *
     * @parameter expression="${maven.yuicompressor.nocompress}" default-value="false"
     */
    private boolean nocompress;

    /**
     * [js only] Minify only, do not obfuscate.
     *
     * @parameter expression="${maven.yuicompressor.nomunge}" default-value="false"
     */
    private boolean nomunge;

    /**
     * [js only] Preserve unnecessary semicolons.
     *
     * @parameter expression="${maven.yuicompressor.preserveAllSemiColons}" default-value="false"
     */
    private boolean preserveAllSemiColons;

    /**
     * [js only] disable all micro optimizations.
     *
     * @parameter expression="${maven.yuicompressor.disableOptimizations}" default-value="false"
     */
    private boolean disableOptimizations;

    /**
     * force the compression of every files,
     * else if compressed file already exists and is younger than source file, nothing is done.
     *
     * @parameter expression="${maven.yuicompressor.force}" default-value="false"
     */
    private boolean force;

    /**
     * a list of aggregation/concatenation to do after processing,
     * for example to create big js files that contain several small js files.
     * Aggregation could be done on any type of file (js, css, ...).
     *
     * @parameter
     */
    private Aggregation[] aggregations;

    /**
     * request to create a gzipped version of the yuicompressed/aggregation files.
     *
     * @parameter expression="${maven.yuicompressor.gzip}" default-value="false"
     */
    private boolean gzip;

    /**
     * show statistics (compression ratio).
     *
     * @parameter expression="${maven.yuicompressor.statistics}" default-value="true"
     */
    private boolean statistics;

    /**
     * aggregate files before minify
     * @parameter expression="${maven.yuicompressor.preProcessAggregates}" default-value="false"
     */
    private boolean preProcessAggregates;

    /**
     * use the input file as output when the compressed file is larger than the original
     * @parameter expression="${maven.yuicompressor.useSmallestFile}" default-value="true"
     */
    private boolean useSmallestFile;

    private long inSizeTotal_;
    private long outSizeTotal_;

    //静态资源版本号
    private static String resourceVersion;

    @Override
    protected String[] getDefaultIncludes() throws Exception {
        return new String[]{"**/*.css", "**/*.js"};
    }

    @Override
    public void beforeProcess() throws Exception {
        if (nosuffix) {
            suffix = "";
        }

        if(preProcessAggregates) aggregate();
    }

    @Override
    protected void afterProcess() throws Exception {
        if (statistics && (inSizeTotal_ > 0)) {
            getLog().info(String.format("total input (%db) -> output (%db)[%d%%]", inSizeTotal_, outSizeTotal_, ((outSizeTotal_ * 100)/inSizeTotal_)));
        }

        if(!preProcessAggregates) aggregate();
    }


    private void aggregate() throws Exception {
        if (aggregations != null) {
            Set<File> previouslyIncludedFiles = new HashSet<File>();
            for(Aggregation aggregation : aggregations) {
                getLog().info("generate aggregation : " + aggregation.output);
                Collection<File> aggregatedFiles = aggregation.run(previouslyIncludedFiles);
                previouslyIncludedFiles.addAll(aggregatedFiles);

                File gzipped = gzipIfRequested(aggregation.output);
                if (statistics) {
                    if (gzipped != null) {
                        getLog().info(String.format("%s (%db) -> %s (%db)[%d%%]", aggregation.output.getName(), aggregation.output.length(), gzipped.getName(), gzipped.length(), ratioOfSize(aggregation.output, gzipped)));
                    } else if (aggregation.output.exists()){
                        getLog().info(String.format("%s (%db)", aggregation.output.getName(), aggregation.output.length()));
                    } else {
                        getLog().warn(String.format("%s not created", aggregation.output.getName()));
                    }
                }
            }
        }
    }

    @Override
    protected void processFile(SourceFile src) throws Exception {

        if(!systemProperty.equalsIgnoreCase("")){
            SystemProperty.initSystemConf(systemProperty);
        }

        if (getLog().isDebugEnabled()) {
            getLog().debug("compress file :" + src.toFile()+ " to " + src.toDestFile(suffix));
        }
        File inFile = src.toFile();
        File outFile = src.toDestFile(suffix);

        getLog().debug("only compress if input file is younger than existing output file");
        if (!force && outFile.exists() && (outFile.lastModified() > inFile.lastModified())) {
            if (getLog().isInfoEnabled()) {
                getLog().info("nothing to do, " + outFile + " is younger than original, use 'force' option or clean your target");
            }
            return;
        }

        InputStreamReader in = null;
        OutputStreamWriter out = null;
        File outFileTmp = new File(outFile.getAbsolutePath() + ".tmp");
        FileUtils.forceDelete(outFileTmp);
        try {
            in = new InputStreamReader(new FileInputStream(inFile), encoding);
            if (!outFile.getParentFile().exists() && !outFile.getParentFile().mkdirs()) {
                throw new MojoExecutionException( "Cannot create resource output directory: " + outFile.getParentFile() );
            }
            getLog().debug("use a temporary outputfile (in case in == out)");

            getLog().debug("start compression");
            out = new OutputStreamWriter(new FileOutputStream(outFileTmp), encoding);

            //InputStreamReader to String
            String stringFromStream = CharStreams.toString(in);


            if(resourceVersion == null){
                resourceVersion=RandomString(9);
            }

            if(systemProperty !=null
                    && !systemProperty.equalsIgnoreCase("")
                    && SystemProperty.getValueParam("system.setVersionStr") != null){
                stringFromStream = stringFromStream.replaceAll(SystemProperty.getValueParam("system.setVersionStr"),"v="+resourceVersion);
            }
            if(systemProperty !=null
                    && !systemProperty.equalsIgnoreCase("")
                    && SystemProperty.getValueParam("system.setCDNUrlStr") != null
                    && SystemProperty.getValueParam("system.CDN_Url") != null) {
                stringFromStream = stringFromStream.replaceAll(SystemProperty.getValueParam("system.setCDNUrlStr"), SystemProperty.getValueParam("system.CDN_Url"));
            }
            if(systemProperty !=null
                    && !systemProperty.equalsIgnoreCase("")
                    && SystemProperty.getValueParam("system.debug") != null){
                stringFromStream = stringFromStream.replaceAll("&debug&",SystemProperty.getValueParam("system.debug"));
            }

            if (nocompress) {
                getLog().info("No compression is enabled");
                IOUtil.copy(in, out);
            } else if (".js".equalsIgnoreCase(src.getExtension())) {
                if(!SystemProperty.getValueParam("system.debug").equals("true")){
                    stringFromStream = compressJs(stringFromStream);
                }
                out.write(stringFromStream);

                //InputStream inputStream = new ByteArrayInputStream(stringFromStream.getBytes(encoding));
                //in = new InputStreamReader(inputStream);
                //JavaScriptCompressor compressor = new JavaScriptCompressor(in, jsErrorReporter_);
                //compressor.compress(out, linebreakpos, !nomunge, jswarn, preserveAllSemiColons, disableOptimizations);
            } else if (".css".equalsIgnoreCase(src.getExtension())) {

                InputStream inputStream = new ByteArrayInputStream(stringFromStream.getBytes(encoding));
                in = new InputStreamReader(inputStream);
                compressCss(in, out);

            } else if(".html".equalsIgnoreCase(src.getExtension()) || ".hbs".equalsIgnoreCase(src.getExtension())){

                HtmlCompressor htmlCompressor = new HtmlCompressor();
                stringFromStream = htmlCompressor.compress(stringFromStream);
                out.write(stringFromStream);
            }
            getLog().debug("end compression");
        } finally {
            IOUtil.close(in);
            IOUtil.close(out);
        }

        boolean outputIgnored = useSmallestFile && inFile.length() < outFile.length();
        if (outputIgnored) {
            FileUtils.forceDelete(outFileTmp);
            FileUtils.copyFile(inFile, outFile);
            getLog().debug("output greater than input, using original instead");
        } else {
            FileUtils.forceDelete(outFile);
            FileUtils.rename(outFileTmp, outFile);
        }

        File gzipped = gzipIfRequested(outFile);
        if (statistics) {
            inSizeTotal_ += inFile.length();
            outSizeTotal_ += outFile.length();

            String fileStatistics;
            if (outputIgnored) {
                fileStatistics = String.format("%s (%db) -> %s (%db)[compressed output discarded (exceeded input size)]", inFile.getName(), inFile.length(), outFile.getName(), outFile.length());
            } else {
                fileStatistics = String.format("%s (%db) -> %s (%db)[%d%%]", inFile.getName(), inFile.length(), outFile.getName(), outFile.length(), ratioOfSize(inFile, outFile));
            }

            if (gzipped != null) {
                fileStatistics = fileStatistics + String.format(" -> %s (%db)[%d%%]", gzipped.getName(), gzipped.length(), ratioOfSize(inFile, gzipped));
            }
            getLog().info(fileStatistics);
        }
    }

    private String compressJs(String codeStr){
        Compiler.setLoggingLevel(Level.OFF);
        Compiler compiler = new Compiler();
        //设置压缩级别
        CompilerOptions options = new CompilerOptions();

        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        //警告级别
        WarningLevel.DEFAULT.setOptionsForWarningLevel(options);
        List<JSSourceFile> externalJavascriptFiles = new ArrayList<JSSourceFile>();
        List<JSSourceFile> primaryJavascriptFiles = new ArrayList<JSSourceFile>();

        primaryJavascriptFiles.add(JSSourceFile.fromCode("", codeStr));

        compiler.compile(externalJavascriptFiles, primaryJavascriptFiles, options);

        Result result=  compiler.getResult();
        if(!result.success){
            System.out.println(result.success);

            JSError[] jsError = compiler.getErrors();
            for(int k=0;k<jsError.length;k++){
                getLog().error("JS Closure Errors:"+ jsError[k].toString());
            }
            return "JS Closure Errors!";
        }
        String[] strings= compiler.toSourceArray();

        return strings[0].toString();
    }

    private void compressCss(InputStreamReader in, OutputStreamWriter out)
            throws IOException {
        try{
            CssCompressor compressor = new CssCompressor(in);
            compressor.compress(out, linebreakpos);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(
                    "Unexpected characters found in CSS file. Ensure that the CSS file does not contain '$', and try again",e);
        }
    }

    protected File gzipIfRequested(File file) throws Exception {
        if (!gzip || (file == null) || (!file.exists())) {
            return null;
        }
        if (".gz".equalsIgnoreCase(FileUtils.getExtension(file.getName()))) {
            return null;
        }
        File gzipped = new File(file.getAbsolutePath()+".gz");
        getLog().debug(String.format("create gzip version : %s", gzipped.getName()));
        GZIPOutputStream out = null;
        FileInputStream in = null;
        try {
            out = new GZIPOutputStream(new FileOutputStream(gzipped));
            in = new FileInputStream(file);
            IOUtil.copy(in, out);
        } finally {
            IOUtil.close(in);
            IOUtil.close(out);
        }
        return gzipped;
    }

    protected long ratioOfSize(File file100, File fileX) throws Exception {
        long v100 = Math.max(file100.length(), 1);
        long vX = Math.max(fileX.length(), 1);
        return (vX * 100)/v100;
    }


    /** 产生一个随机的字符串*/
    protected static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}
