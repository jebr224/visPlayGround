import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
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
      
    	 inImage = ImageIO.read(new File("cool.png"));
    	 outImage = ImageIO.read(new File("cool.png"));
        //Graphics2D g = inImage.createGraphics();
 
        JFrame frame = new JFrame("Vis play ground");
        frame.setSize(inImage.getWidth()+outImage.getWidth(), inImage.getHeight());
        
        frame.getContentPane().add(new imagePanel());
       /* 
        int x,y;
        for(y =0;y <inImage.getHeight(); y++)
        {
        	for(x=0;x<inImage.getWidth(); x++)
        		{
        			
        			inImage.setRGB(x, y, ((inImage.getRGB(x, y))&0xFFFF00));
        		}
        }
       */
        
        int x,y;
       // filterPixs = new int[9];
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
        			
        			int i;
        			int r=0,g=0,b=0, l=0; //
        			
        			//setting up simple filters
        			//int[] filter ={0,-3,0,-3,26,-3,0,-3,0}; 
        			//int[] filter ={1,1,1,1,1,1,1,1,1} ;//0,-3,0,0,-3,21,-3,0,-3,0}; 
        			//int[] filter = {0,3,0,3,-36,3,0,3,0};
        			//int[] filter = {0, 9,0,9,-36,9,0,9,0}; //standard image edge detection
        			//int[] filter = {0, -9,0,-9,36,-9,0,-9,0};
        			int[] filter = {0,18,0,18,-72,18,0,18,0};
        			for(i =0; i<9; i++)
        			{
        				//Before pixel class
        				//l = l + (filter[i]*((filterPixs[i]>>24)&0x000000ff));//& 0x0000ff;
        				//r = r + (filter[i]*((filterPixs[i]>>16)&0x000000ff));//& 0x00ff0000;
        				//g = g + (filter[i]*((filterPixs[i]>>8)&0x000000ff));//& 0x0000ff00;        				
        				//b = b + (filter[i]*(filterPixs[i]&0x000000ff));//& 0x000000ff;
        				
        				l = l + filter[i]*surroundingPix[i].getDark();
        				r = r+ filter[i] *surroundingPix[i].getRed();
        				g = g+ filter[i] *surroundingPix[i].getGreen();
        				b = b +filter[i] *surroundingPix[i].getBlue();

        			}
        			
        			
        			float fl =(float) (l/9.0);
        			float fr =(float) (r/9.0);
        			float fb =(float) (b/9.0);
        			float fg =(float) (g/9.0);
        			/*
        			if(l>0x000000ff) l = 0x000000ff;
        			if(r>0x000000ff) r = 0x000000ff;
        			if(g>0x000000ff) g = 0x000000ff;
        			if(b>0x000000ff) b = 0x000000ff;
					*/
        			int pixlOut = outImage.getRGB(x,y);
        			int outL = (pixlOut & 0xff000000) >> 24; 
        			int outR = (pixlOut & 0x00ff0000) >> 16;
        			int outG = (pixlOut & 0x0000ff00) >> 8;
        			int outB  =pixlOut & 0x000000ff;
        			l = (int) ((outL)* fl);
        		    r = (int) ((outR)* fr);
        		 	b = (int) ((outB)* fb);
        	     	g = (int) ((outG)* fg);
        	     	
        	     	l = l + 10000;
        	     	r = r + 10000;
        	     	b = b + 10000;
        	     	g = g + 10000;
        			
        			if(l>0x000000ff) l = 0x000000ff;
        			if(r>0x000000ff) r = 0x000000ff;
        			if(g>0x000000ff) g = 0x000000ff;
        			if(b>0x000000ff) b = 0x000000ff;
					
        			
        			
        			outImage.setRGB(x,y,(l << 24 | r<<16 |  g<<8 | b));
        			//inImage.setRGB(x, y, ((inImage.getRGB(x, y))&0xFFFF00));
        		}

        }
        
        frame.getContentPane().add(new imagePanel());

        
        frame.setVisible(true);
        
        
        
    }
	
	
	
}
