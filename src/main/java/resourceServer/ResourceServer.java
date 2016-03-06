package resourceServer;

import sax.ReadXMLFileSAX;
import resources.TestResource;

/**
 * Created by Victor on 07.03.2016.
 */
public class ResourceServer implements ResourceServerI{

    private String name;
    private int age;

    public ResourceServer() {
        name = "";
        age = 0;
    }

    public ResourceServer(String path) {
        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(path); //"./resources/testResource.xml");
        if (resource != null) {
            name = resource.getName();
            age = resource.getAge();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ResourceServer{" +
                "name='" + name + '\'' +
                ", age='" + String.valueOf(age) + '\'' +
                '}';
    }


}
