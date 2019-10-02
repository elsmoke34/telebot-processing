import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class songibi extends PApplet {

  


Serial myPort;                             // serial port used for communication
String val;  
boolean firstContact = false;              // true if serial comm is stablished

PImage ileri;
PImage asagi;
PImage sag;
PImage sol;
PImage kontrolpaneli;

int lm35;
int mq135; 

int ilerix = 1580;
int ileriy = 300;
int asagix = 1580;
int asagiy = 500;
int sagx = 1680;
int sagy = 400;
int solx = 1485;
int soly = 400;
int i=0;
String ds;

Capture cam;

public void setup()
{
  
  
  myPort = new  Serial(this, "COM5", 9600);  // serial comm initialization
  myPort.bufferUntil('\n'); // stores all characters in buffer until receives a "new line" ('\n')
  
  ileri= loadImage("yukaritus.jpg");
  asagi= loadImage("asagitus.jpg");
  sag= loadImage("sagtus.jpg");
  sol= loadImage("soltus.jpg");
  kontrolpaneli= loadImage("kontrolpaneliarkaplan.jpg");
  
   String[] cameras = Capture.list();
   
  
  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    println("Available cameras:");
    for (int i = 0; i < cameras.length; i++) {
      println(cameras[i]);
    }
    
    // The camera can be initialized directly using an 
    // element from the array returned by list():
    cam = new Capture(this, cameras[13]);
    cam.start();     
  }      
}

public void draw()
{
  background(kontrolpaneli);
  image(ileri,ilerix,ileriy);
  image(asagi,asagix,asagiy);
  image(sag,sagx,sagy);
  image(sol,solx,soly);
  image(kontrolpaneli,1920,1080);
  
  textAlign(CENTER);
  text(lm35,500,500);
  textSize(40);
  //text("Sıcaklık: ", 100, 250);
  text(mq135,500,670);
  textSize(40);
  //text("Hava Kalitesi: ", 140, 150);
  
  if (cam.available() == true) {
    cam.read();
  }
  image(cam, 650,350);
}

public void serialEvent(Serial myPort)
{
  val = myPort.readStringUntil('\n'); 
  
  if (val != null){
    val = trim(val);                   // eliminates spaces and special characters
    println(val);                      // prints the string on the console
      if (val.charAt(0)=='A')
        lm35 = PApplet.parseInt(val.substring(1)); // transforms the numbers into one integer value
      if (val.charAt(0)=='B')
        mq135 = PApplet.parseInt(val.substring(2)); // transforms the numbers into one integer value
    }
  }



 public void mousePressed()
{
   if (ilerix < mouseX && (ilerix + ileri.width>mouseX) && (ileriy<mouseY) && ileriy+ileri.height>mouseY) 
      {
      
       ds = "F";
       myPort.write("z" + ds+"\n");
       //background(0);
      } 
      
      if (asagix < mouseX && (asagix + asagi.width>mouseX) && (asagiy<mouseY) && asagiy+asagi.height>mouseY) 
      {
       //i=0;
       ds = "B";
       myPort.write("z" + ds+"\n");
      } 
      
      if (sagx < mouseX && (sagx + sag.width>mouseX) && (sagy<mouseY) && sagy+sag.height>mouseY) 
      {
       //i=255;
       ds = "L";
       myPort.write("z" + ds+"\n");
      } 
      
      if (solx < mouseX && (solx + sol.width>mouseX) && (soly<mouseY) && soly+sol.height>mouseY) 
      {
       //i=0;
       ds = "R";
       myPort.write("z" + ds+"\n");
       
      } 
      

}
  public void settings() {  size(1920,1080); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "songibi" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
