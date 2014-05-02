package com.naroky.cookingthai;



import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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

import com.google.ads.*;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class MainActivity extends Activity  implements
OnItemClickListener {
	static final int PROGRESS_DIALOG = 0;
	private boolean processResult;
	private ArrayList<Object> itemList;
	private String ar_title[];
	private String ar_id[];
	private String ar_thumb[];
	private String ar_playlist_url[];
	private String ar_playlist_id[];
	
	private MainActivity instant = this;
	private itemBean bean;
	
	private ListView lview;
	private ListViewAdapter adapter;	
	private String Query = "";
	private String dataUrl;
	private String encodedURL;
	private TextView txtstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bannerLib bannerObj = new bannerLib();
		bannerObj.createBanner(this);

		processResult = false;


		dataUrl = "http://www.naroky.com/foodcontent/index.php/group_foods/xml";	
 		txtstatus = (TextView) instant.findViewById(R.id.status);
 		txtstatus.setText("Loading ..."); 
		openPage1();
    
    }


	private void openPage1() {
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
        			//FileInputStream fis = this.openFileInput("indream.xml");
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
	        	 lview = (ListView) instant.findViewById(R.id.listView_item);
	        	 adapter = new ListViewAdapter(instant, itemList);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		
		String id = "";
		String title = "";
		String description = "";
		
        Intent intent = new Intent(MainActivity.this, ListFoodActivity.class);
        intent.putExtra("group_id", ar_id[position]);
        intent.putExtra("group_title", ar_title[position]);
        //intent.putExtra("indream_description", ar_description[position]);
		
		/*
		Intent i = new Intent(Intent.ACTION_VIEW, 
		Uri.parse("http://www.youtube.com/playlist?list=" + ar_playlist_id[position]));
		*/
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

