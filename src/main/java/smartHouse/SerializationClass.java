package smartHouse;

import java.beans.XMLEncoder;
import java.io.*;

public abstract class SerializationClass {

    public static void serialize(Object serialize) {
        try {
            FileOutputStream fos = new FileOutputStream("temp.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(serialize);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SmartHouse deSerialize(){
        try {
            FileInputStream fis = new FileInputStream("temp.txt");
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (SmartHouse) oin.readObject();
        }
        catch (Exception e){

        }
        return null;
    }

    public static void serializeToXML(SmartHouse smartHouse){
        XMLEncoder encoder=null;
        try{
            encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("smartHouse.xml")));
            encoder.writeObject(smartHouse);
            encoder.close();
        }catch(FileNotFoundException fileNotFound){
            System.out.println("ERROR: While Creating or Opening the File smartHouse.xml");
        }
    }
}
