package cn.com.bestpay.portal.mojo;


import cn.com.bestpay.portal.utils.SourceFile;

import java.io.File;

/**
 * Check JS files with jslint.
 *
 * @goal jslint
 * @phase process-resources
 *
 * @threadSafe
 */
// @SuppressWarnings("unchecked")
public class JSLintMojo extends MojoSupport {
    private JSLintChecker jslint_;

    @Override
    protected String[] getDefaultIncludes() throws Exception {
        return new String[] { "**/**.js" };
    }

    @Override
    public void beforeProcess() throws Exception {
        jslint_ = new JSLintChecker();
    }

    @Override
    protected void beforeProcess(File warSourceDirectory, File webappDirectory) throws Exception {

    }

    @Override
    public void afterProcess() throws Exception {
    }

    @Override
    protected void processFile(SourceFile src) throws Exception {
        getLog().info("check file :" + src.toFile());
        jslint_.check(src.toFile(), jsErrorReporter_);
    }
}
