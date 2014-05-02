package com.naroky.cookingthai;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DetailFoodActivity extends Activity {
	private ArrayList<Object> itemList;
	private String ar_title[];
	private String ar_id[];
	private String ar_thumb[];
	private String ar_group[]; 
	private String ar_ingredient[];
	private String ar_how[];
	private String dataUrl;
	
	private TextView TV_food_title; 
	private TextView TV_food_how;
	private TextView TV_food_ingredient;
	private TextView TV_food_group;
	
	private String content_id;
	private String group_id;
	private String group_title;
	
	private DetailFoodActivity instant = this;
	private itemBean bean;

	private TextView txtstatus;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_food);
	    Bundle bundle = getIntent().getExtras();
	    content_id = bundle.getString("food_id");
	    group_title = bundle.getString("group_title");
	    group_id = bundle.getString("group_id");
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		
	    String group_title_encode = "";
	    
		dataUrl = "http://www.naroky.com/foodcontent/index.php/foods/xml_detail/"+ content_id ;	
		Log.d("onCreate Call URL:",dataUrl);
		//txtstatus = (TextView) instant.findViewById(R.id.status);
 		//txtstatus.setText("Loading ..."); 
 		loadFoodDetail();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_food, menu);
		return true;
	}
	private void loadFoodDetail() {
   		itemList = new ArrayList<Object>();
		class RequestTask extends AsyncTask<String, String, String>{

	        @Override
	        protected String doInBackground(String... uri) {
	             //http request here
	        	HttpClient httpclient = new DefaultHttpClient();
	            HttpResponse response;
	            String responseString = null;
	            try {

	        		//ArrayList<Object> itemList;
	 
	        		String strTextTitle = "";
	        		

        			Log.d("openPage1", uri[0]);
        		    URL url = new URL(uri[0]);
        		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        			Log.d("openPage1", "Step 1");
        			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        			Log.d("openPage1", "Step 2");
        			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        			Log.d("openPage1", "Step 3");
        			DocumentBuilder db = dbf.newDocumentBuilder();
        			Log.d("openPage1", "Step 4");

        			Document doc = db.parse(in);
        			Log.d("openPage1", "Step 5");

        			doc.getDocumentElement().normalize();
        			Log.d("openPage1", "Step 6");
        			NodeList item_Nodelist = doc.getElementsByTagName("item");
        			Log.d("openPage1", "Step 7");
        			itemList = new ArrayList<Object>();
        			Log.d("openPage1", "Step 8");
        			int nodeCount = (int) item_Nodelist.getLength();
        			Log.d("openPage1", "Step 9 =>" + item_Nodelist.getLength());
        			ar_id = new String[nodeCount];
        			ar_title = new String[nodeCount];
        			//ar_description = new String[nodeCount];
        			ar_thumb = new String[nodeCount];
        			ar_group = new String[nodeCount];
        			ar_ingredient = new String[nodeCount];
        			ar_how = new String[nodeCount];        			
        			for (int i = 0; i < item_Nodelist.getLength(); i++) {
        				Node firstPersonNode = item_Nodelist.item(i);
        				if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
        					Log.d("openPage1", "Parser");
        					Element firstPersonElement = (Element) firstPersonNode;

        					// -------
        					strTextTitle = getNodeValue("title", firstPersonElement);
        					Log.d("openPage1", "Get:" + strTextTitle);
        					ar_id[i] = getNodeValue("id", firstPersonElement);
        					ar_title[i] = strTextTitle;
        					ar_thumb[i] = getNodeValue("thumbnail", firstPersonElement);
        					ar_group[i] = getNodeValue("group", firstPersonElement);
        					ar_ingredient[i] = getNodeValue("ingredient", firstPersonElement);
        					ar_how[i] = getNodeValue("how", firstPersonElement);

    
        				}
            			Log.d("openPage1", "Loop" + i);
        			}	        		
	            } catch (Exception e) {
	                //TODO Handle problems..
	            	Log.d("openPage1", "Error:" + e);
	            }
	            return responseString;
	            //return the response as string
	        };
	        @Override
	        protected void onPostExecute(String result) {
	        	 super.onPostExecute(result);
	        	 Log.d("onPostExecute", "onPostExecute:");
	        	 TV_food_title = (TextView) instant.findViewById(R.id.food_title);
	        	 TV_food_title.setText(ar_title[0]);
	        	 TV_food_group = (TextView) instant.findViewById(R.id.food_group);
	        	 TV_food_group.setText(ar_group[0]);
	        	 TV_food_how = (TextView) instant.findViewById(R.id.food_how);
	        	 TV_food_how.setText(ar_how[0]);
	        	 TV_food_ingredient = (TextView) instant.findViewById(R.id.food_ingredient);
	        	 TV_food_ingredient.setText(ar_ingredient[0]);
	        	 /*
	        	 lview = (ListView) instant.findViewById(R.id.listView_fooditem);
	        	 adapter = new ListFoodAdapter(instant, itemList);
	        	 lview.setAdapter(adapter);
	     		 lview.setOnItemClickListener((OnItemClickListener) instant);
	     		 */
	     		 //txtstatus.setText(""); 

	        }
	            //set the the data you get
	    }		

		
		RequestTask getXML = new RequestTask();
		getXML.execute(dataUrl);
		
	}
	private void AddObjectToList(String image, String title, String target) {
		Log.d("AddObjectToList", "Step 1");
		bean = new itemBean();
		//bean.setImage(image);
		bean.setTitle(title);
		itemList.add(bean);
	}	
	private String getNodeValue(String NodeName, Element ElementObj) {
		try
		{
			NodeList nodeObj_List = ElementObj.getElementsByTagName(NodeName);

			Element subElementObj = (Element) nodeObj_List.item(0);
			NodeList childNode_List = subElementObj.getChildNodes();
			String nodeValue = ((Node) childNode_List.item(0)).getNodeValue()
					.trim();

			return (nodeValue);
		}
		catch (Exception e) {
        	Log.d("openPage1", "Error:" + e);			
			return "";
			
		}
	}
	public void onBackPressed() 
	{
	
		Intent i = new Intent(this, ListFoodActivity.class);
		i.putExtra("group_id", group_id);
		i.putExtra("group_title", group_title); 
		
		startActivity(i);
		//Intent intent = new Intent(ListFoodActivity.this, DetailFoodActivity.class);
	    //intent.putExtra("food_id", ar_id[position]);		
		 finish();
	}	

	
	
}

