package edu.ifsudestemg.barbacena.webchart;

import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

public class MakeGraph{
	private final int FONT_SIZE = 18;
	private final int NUM_LINES = 5;
	private Canvas canv;
	
	public Bitmap graph(int width, int height){
	
		Bitmap graph = Bitmap.createBitmap(width, height+50, Bitmap.Config.ARGB_8888);
		canv = new Canvas(graph);
		
		drawGrid(canv, width, height, NUM_LINES);
		return graph;
	}
	
	private void drawGrid(Canvas canvas, int width, int height, int num_lin){
		float beginy, beginx, endy, endx;
		
		beginy = (float) (height*0.05);
		beginx =  (float) (width*0.05) + 20;
		endy = (float) (height*0.95);
		endx = (float) (width*0.95) + 20;
		
		Paint paint = new Paint();
		
		for(int i = 1; i < num_lin; i++){
			float interx = (width-beginx)/num_lin;
			float intery = (height-beginy)/num_lin;
			
			paint.setARGB(200, 224, 224, 224);
			canvas.drawLine(beginx+interx*i, beginy, beginx+interx*i, endy, paint);
			canvas.drawLine(beginx, endy-intery*i, endx, endy-intery*i, paint);
		}
		
		paint.setARGB(255, 46, 0 , 184);
		canvas.drawLine(beginx, beginy, beginx, endy, paint);
		canvas.drawLine(beginx, endy, endx, endy, paint);
		
		paint.setTextSize(FONT_SIZE);
		canvas.drawText("0", beginx-15, endy, paint);
		canvas.drawText("100", beginx-33, beginy + 10, paint);
		
	}
	
	public Bitmap drawBarData(Bitmap img, int width, int height, List<Float> dados, List<String> label){
		
		Canvas canvas = new Canvas(img);
		
		float beginy, beginx, endy, endx;
		int standard = 50;
		beginy = (float) (height*0.05);
		beginx =  (float) (width*0.05) + 20;
		endy = (float) (height*0.95);
		endx = (float) (width*0.95) + 20;
		
		Paint paint = new Paint();
		Typeface tf = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL);
		paint.setTypeface(tf);
		paint.setTextSize(FONT_SIZE);
		
		while(standard*dados.size() + 3*dados.size() + 20 > width)
			standard = standard/2;
				
		float begin = (width - (standard*dados.size() + 3*dados.size()))/2 + 20;
		float ex = beginx;
		float by = endy;
		
		for(int i = 0; i < dados.size(); i++ ){
			
			Random rand = new Random();
			paint.setARGB(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
			float d = dados.get(i);
						
			Log.d(HttpQuery.MY_APP_TAG, " " + endy + " - " + (d * (endy-beginy)/100));
			
			float y = endy - (d * (endy-beginy)/100);
			
			Log.d(HttpQuery.MY_APP_TAG, " " + begin + " " + endy + " " + begin+standard + " " + y);
			canvas.drawRect( begin, endy, begin+standard, y, paint);
			canvas.drawText( Float.toString(d) , begin , y-10 , paint);
			
			begin = begin + standard + 3;
			
			if( i % 2 == 0){
				ex = beginx;
				by = by + 20;
			}
			else{
				ex = endx/2 ;
			}
			canvas.drawText( label.get(i) , ex , by , paint);
		}
		
		return img;
	}
	
}
