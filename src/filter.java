import java.awt.image.BufferedImage;
import java.util.ArrayList;




public class filter {
    ArrayList<Integer> data_m;
    int elements_m =0;
	//ArrayList data_m = new ArrayList[1];
	
	public filter(int dataIn[], int elements, String name){
	    data_m = new ArrayList<Integer>();
		elements_m = elements;
	    for(int i =0; i < elements; i++)
		{
			data_m.add(dataIn[i]); 
		}
		
	}
	public pixel useFilter( pixel[] blockOfPix)
	{
		int redSum = 0, greenSum =0,blueSum =0;
		for(int i =0; i < elements_m; i++)
		{
			redSum = redSum + data_m.get(i) * blockOfPix[i].getRed();
			greenSum = greenSum + data_m.get(i)*blockOfPix[i].getGreen();
			blueSum = blueSum + data_m.get(i)* blockOfPix[i].getBlue();
		}
		float redScale =  (float) redSum ;
		float greenScale = (float)  greenSum;
		float blueScale =  (float) greenSum;
		
		redScale = redScale / elements_m;
		greenScale = greenScale / elements_m;
		blueScale = blueScale / elements_m;
		
		pixel centerPix= new pixel(blockOfPix[4].getPix()); //Change for more then 3 by 3
		
		int nwRed=(int) ( centerPix.getRed()* redScale  +2000);
		int nwGreen =(int) (centerPix.getGreen() * greenScale+2000);
		int nwBlue =(int) (centerPix.getBlue() * blueScale+2000);
		
		if(nwRed > 0x000000ff) nwRed = 0x000000ff;
		if(nwBlue > 0x000000ff) nwBlue = 0x000000ff;
		if(nwGreen > 0x000000ff) nwGreen = 0x000000ff;
		
		centerPix.setRed((short)nwRed);
		centerPix.setGreen((short)nwGreen );
		centerPix.setBlue((short)nwBlue);
		
		return centerPix;
		
		
	}
	
	public BufferedImage blackWhite( BufferedImage inImage, int[] filterData, int limit)
	{
		//byte r,g,b;
		BufferedImage outImage = new BufferedImage(inImage.getWidth(), inImage.getHeight(),BufferedImage.TYPE_INT_RGB);
		for(int y = 0; y < inImage.getHeight(); y++)
		{
			outImage.setRGB(0, y, 0xffffff00);
			outImage.setRGB(inImage.getWidth()-1, y, 0xffffff00);
		}
		for(int x = 0; x < inImage.getWidth(); x++)
		{
			outImage.setRGB(x,0,0xffffff00);
			outImage.setRGB(x,inImage.getHeight()-1,0xffffff00);

		}
		
		for(int y = 1; y < inImage.getHeight()-1 ; y++)
		{
			for(int x =1; x< inImage.getWidth() -1; x++)
			{
				long total = 0;
				
				
				for(int i=0; i < 3; i++ )
				{
					for( int j = 0; j < 3; j++)
					{
						int temp = inImage.getRGB(i+x-1, j+y-1);
						long blackWhite = (temp & 0x000000ff) +(temp& 0x0000ff00 >> 16) + (temp & 0x00ff0000 >> 24);
						//System.out.print(blackWhite);
						total = total +  blackWhite * filterData[i*3+j];
					}
				}
				//System.out.print(total);
				if (total < limit)
					outImage.setRGB(x, y,  0xffffffff);
				else
					outImage.setRGB(x,y,  0x00000000);
			}
		}
		
		
		return outImage;
	}
	
	
	
	
}
		