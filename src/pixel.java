
	public class pixel{
		int data_m;
		short l;
		short R;
		short G;
		short B;
		
		public pixel(int dataIn)
		{
			data_m = dataIn;
			l = (short) ((data_m & 0xff000000) >> 24);
			R = (short) ((data_m & 0x00ff0000) >> 16);
			G = (short) ((data_m & 0x0000ff00) >> 8);
			B = (short) ((data_m & 0x000000ff) >> 0);
		}
		public short getDark(){
			return l;
		}
		public short getRed(){
			return R;
		}
		public short getBlue()	{
			return B;
		}
		public short getGreen(){
			return G;
		}
		public int getPix(){
			return data_m;
		}
		public void setDark(short Din){
			l = Din;
			l = (short) ((data_m & 0x00ffffff) >> 24);

			
		}
		public void setRed(short Rin){
			R = Rin;
			data_m = ((data_m & 0xff00ffff) | (((int) R) << 16));

		}
		public void setBlue(short Bin){
			B = Bin;
			data_m = ((data_m & 0xffff00ff) | (((int) B) << 8));
		}
		public void setGreen(short Gin){
			G = Gin;
			data_m = ((data_m & 0xffffff00) | (((int) G)));
		}
		public void setPix(int pixIn){
			data_m = pixIn;
			l = (short) ((data_m & 0xff000000) >> 24);
			R = (short) ((data_m & 0x00ff0000) >> 16);
			G = (short) ((data_m & 0x0000ff00) >> 8);
			B = (short) ((data_m & 0x000000ff) );//>> 0);
		}
}
