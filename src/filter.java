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
	public pixel appleFilter( pixel[] blockOfPix)
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
	
	
	
}
		