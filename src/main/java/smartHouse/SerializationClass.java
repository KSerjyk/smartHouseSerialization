package smartHouse;

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
            SmartHouse sm = (SmartHouse) oin.readObject();
            return sm;
        }
        catch (Exception e){

        }
        return null;
    }
}
