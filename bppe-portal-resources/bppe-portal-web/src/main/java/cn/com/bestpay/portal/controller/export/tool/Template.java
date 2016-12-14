package cn.com.bestpay.portal.controller.export.tool;

import cn.com.bestpay.portal.controller.export.model.Student;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.StringRenderer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by susie on 2016/12/14.
 */
public class Template {
    public static void main(String[] args) throws FileNotFoundException {
        Template exportFile = new Template();
       exportFile.template();
    }
    /**
     *
     * @throws FileNotFoundException
     */
    public void template() throws FileNotFoundException {


        ST st = new ST("hello,$name$");
        st.add("name", "china");

        System.out.println(st.toString());

        ST st2 = new ST("select $columns:{<i>$it$</i>\n}$ from users");
        List<String> columns = new ArrayList<String>();
        columns.add("a");
        columns.add("b");
        columns.add("c");
        columns.add("d");
        columns.add("e");
        st2.add("columns",columns);
        System.out.println(st2.toString());

        ST st3 = new ST("$students:{" +
                "$it.name$," +
                "$it.age$," +
                "$it.hobbys:{$it$,}$" +
                "}$");
        List<Student> students = new ArrayList<Student>();
        Student student = new Student();
        student.setName("hunter");
        student.setAge(24);
        List<String> hobbyList = new ArrayList<String>();
        hobbyList.add("sports");
        hobbyList.add("grils");
        hobbyList.add("money");
        student.setHobbys(hobbyList);
        students.add(student);

        student = new Student();
        student.setName("zhangzehao");
        student.setAge(25);
        hobbyList = new ArrayList<String>();
        hobbyList.add("movie");
        hobbyList.add("coding");
        student.setHobbys(hobbyList);
        students.add(student);

        st3.add("students", students);

        System.out.println(st3.toString());

    }
}
