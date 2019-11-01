/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp_ex3_object;

import UDP.Student;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LeHoa
 */
public class Client {
     private static final long serialVersionUID = 20151107;
    
    public static void main(String[] args) {
        
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            
            //Gui ma sinh vien
            Student std = new Student("B16DCCN151");
            
            //convert object to byte
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(std);
            
            byte[] senCode = baos.toByteArray();
            DatagramPacket senPacket = new DatagramPacket(senCode, senCode.length, InetAddress.getByName("localhost"), 1109);
            datagramSocket.send(senPacket);
            System.out.println("Gui thanh cong");
            
            //Nhan lai 1 sinh vien
            
            byte[] data = new byte[1024];
            DatagramPacket dataPacket = new DatagramPacket(data, data.length);
            datagramSocket.receive(dataPacket);
            
            //covert byte to object
            ByteArrayInputStream bais = new ByteArrayInputStream(dataPacket.getData());
            ObjectInputStream in = new ObjectInputStream(bais);
            Student student = (Student) in.readObject();
            
            System.out.println("Nhan duoc sinh vien: " + student);
            
            //Xu ly
            float gpaa = student.getGpa();
            if(gpaa>=3.7 && gpaa <=4.0){
                student.setGpa('A');
            }else if(gpaa>=3.0 && gpaa<3.7){
                student.setGpa('B');
            }else if(gpaa>=2.0 && gpaa<3.0){
                student.setGpa('C');
            } else if(gpaa>=1.0 && gpaa<2.0){
                student.setGpa('D');
            } else{
                student.setGpa('F');
            }
            
            //Gui lai:
            baos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(baos);
            out.writeObject(student);
            
            byte[] datasending = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(datasending, datasending.length, InetAddress.getByName("localhost"), 1109);
            datagramSocket.send(packet);
            System.out.println("gui thanh cong");
            
            
            //Dong ket noi
            in.close();
            out.close();
            
            
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
             Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    
}
