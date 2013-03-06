import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;





public class imagePanel extends JPanel {
private static BufferedImage inImage, outImage;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static int[] filterPixs;


	

	public void paint(Graphics g) {		
			  g.drawImage(outImage, 0,0,this);
			  g.drawImage(inImage, outImage.getWidth(), 0, this);
		  }
	
	
	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
	    int imageWidth  = image.getWidth();
	    int imageHeight = image.getHeight();

	    double scaleX = (double)width/imageWidth;
	    double scaleY = (double)height/imageHeight;
	    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
	    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

	    return bilinearScaleOp.filter(
	        image,
	        new BufferedImage(width, height, image.getType()));
	}

	
    public static void main(String[] args) throws IOException 
    {
    	System.out.print("hello john");
    	 double  dispScale = 1/2.0;
    	 inImage = ImageIO.read(new File("chad.png"));
    	 outImage = ImageIO.read(new File("chad.png"));
 
        JFrame frame = new JFrame("Vis play ground");
        frame.setLayout(new FlowLayout(FlowLayout.LEFT)); 

       
        JLabel inDisp = new JLabel(new ImageIcon( inImage.getScaledInstance((int)(inImage.getWidth()*dispScale),(int)( inImage.getHeight()*dispScale), 1)));
        
        inDisp.setLocation(0, 0);
        inDisp.setSize(100,100);
        frame.getContentPane().add(inDisp);
        
		int[] filter = {0,18,0,18,-72,18,0,18,0}; //best edge detection
        filter cool = new filter(filter,9,"games");
        BufferedImage outImage_B=  cool.blackWhite(inImage, filter, 1200);
        int[] blur = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
        BufferedImage image_C = cool.blackWhite(outImage_B, blur, -2000 );
        BufferedImage image_D = cool.blackWhite(image_C, blur, -2000 );

        outImage = cool.blackWhite(image_D, blur, -2000 );

        
        /* 5:11
        int x,y;
        for(y =1;y <inImage.getHeight()-4; y++)
        {
        	for(x=1;x<inImage.getWidth()-4; x++)
        		{
        			
        			pixel surroundingPix[];
        			surroundingPix = new pixel[9];
        			int filterPixs[] = {0,0,0,0,0,0,0,0,0};
        			//inImage.getRGB(x-1, y-1, 3, 3, filterPixs, 0, 9);
        			
        			
        			for(int g = 0; g < 3; g++)
        			{
        				for(int h=0; h<3;h++)
        				{
        					filterPixs[g*3+h] = inImage.getRGB(x+h, y+g);
        					surroundingPix[g*3+h] = new pixel(  inImage.getRGB(x+h, y+g) );
        				}
        			}
  					
        			
        			//setting up simple filters
        			//int[] filter ={0,-3,0,-3,26,-3,0,-3,0}; 
        		//	int[] filter ={1,1,1,1,1,1,1,1,1} ;//0,-3,0,0,-3,21,-3,0,-3,0}; 
        			//int[] filter = {0,3,0,3,-36,3,0,3,0};
        			//int[] filter = {0, 9,0,9,-36,9,0,9,0}; //standard image edge detection
        			
        			//int[] filter = {0, -4,0,-4,16,-4,0,-4,0};// second best
        			
        			
        			int[] filter = {0,18,0,18,-72,18,0,18,0}; //best edge detection
        			
        			//int[] filter = {0,18,0,18,-72,18,0,18,0};

        			
        		
        			
        	
        			
        			
        			filter edgeDectect = new filter(filter , 9, "edge_Dect");
        			pixel pixOut = edgeDectect.useFilter(surroundingPix);
        			//outImage.setRGB(x,y,(l << 24 | r<<16 |  g<<8 | b));
        			outImage.setRGB(x,y,pixOut.getPix());
        			//inImage.setRGB(x, y, ((inImage.getRGB(x, y))&0xFFFF00));
        		}

        }
        */ //5:11
        
        
        
       // frame.getContentPane().add(new imagePanel());
        JLabel outDisp = new JLabel(new ImageIcon( outImage.getScaledInstance((int)(inImage.getWidth()*dispScale), (int) (inImage.getHeight()*dispScale), 1)));
        frame.getContentPane().add(outDisp);
        //outDisp.setLocation(0, 100);
        //outDisp.setSize(100,100);
        
        frame.setSize(1000,1000);

        frame.setVisible(true);
        
        
        
    }
	
	
	
}
