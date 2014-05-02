package com.naroky.cookingthai;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListFoodActivity extends Activity  implements
OnItemClickListener{
	static final int PROGRESS_DIALOG = 0;
	private boolean processResult;
	private ArrayList<Object> itemList;
	private String ar_title[];
	private String ar_id[];
	private String ar_thumb[];
	private String ar_playlist_url[];
	private String ar_playlist_id[];
	
	private ListFoodActivity instant = this;
	private itemBean bean;
	
	private ListView lview;
	private ListFoodAdapter adapter;	
	private String Query = "";
	private String dataUrl;
	private String encodedURL;
	private TextView txtstatus;
	private int offset = 0;
	private int limit = 30;
	
	private String group_id;
	private String group_title; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_food);

		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		
	    Bundle bundle = getIntent().getExtras();
	    group_id = bundle.getString("group_id");
	    group_title = bundle.getString("group_title");
	    String group_title_encode = "";
	    try {

	        group_title_encode = URLEncoder.encode(group_title,"UTF-8");

	    } catch (Exception e) {
	        e.printStackTrace();
	    } 
		
		dataUrl = "http://www.naroky.com/foodcontent/index.php/foods/xml/"+ group_title_encode +"/" + offset + "/" + limit + "/";	
		Log.d("onCreate Call URL:",dataUrl);
 		txtstatus = (TextView) instant.findViewById(R.id.status);
 		txtstatus.setText("Loading ..."); 
 		loadFood();
	}

	private void loadFood() {
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
        			ar_playlist_url = new String[nodeCount];
        			ar_playlist_id = new String[nodeCount];        			
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
        					//ar_thumb[i] = getNodeValue("image", firstPersonElement);
        					//ar_playlist_url[i] = getNodeValue("playlist_url", firstPersonElement);
        					//ar_playlist_id[i] = getNodeValue("playlist_id", firstPersonElement);
        					//ar_description[i] = getNodeValue("description", firstPersonElement);
        					AddObjectToList(ar_thumb[i], strTextTitle, "");
        				}
            			Log.d("openPage1", "Loop" + i);
        			}	        		
	            } catch (Exception e) {
	                //TODO Handle problems..
	            	Log.d("openPage1", "Error:" + e);
	            }
	            return responseString;
	            //return the response as string
	        }
	        @Override
	        protected void onPostExecute(String result) {
	        	 super.onPostExecute(result);
	        	 Log.d("onPostExecute", "onPostExecute:");
	        	 lview = (ListView) instant.findViewById(R.id.listView_fooditem);
	        	 adapter = new ListFoodAdapter(instant, itemList);
	        	 lview.setAdapter(adapter);
	     		 lview.setOnItemClickListener((OnItemClickListener) instant);
	     		txtstatus.setText(""); 

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
		startActivity(new Intent(this, MainActivity.class));
		 finish();
	}		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_food, menu);
		return true;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		
        Intent intent = new Intent(ListFoodActivity.this, DetailFoodActivity.class);
        intent.putExtra("food_id", ar_id[position]);
        intent.putExtra("group_id", group_id);
        intent.putExtra("group_title", group_title);

		startActivity(intent);        

		finish(); 
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// respond to menu item selection

		switch (item.getItemId()) {
		case R.id.action_about:
			startActivity(new Intent(this, AboutActivity.class));
			finish(); 
			return true;
		case R.id.action_help:
			startActivity(new Intent(this, HelpActivity.class));
			finish(); 
			return true;

		case R.id.action_exit:
			finish(); 
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	    
}
