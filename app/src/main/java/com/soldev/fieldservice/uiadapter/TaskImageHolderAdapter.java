package com.soldev.fieldservice.uiadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.soldev.fieldservice.DisplayImageActivity;
import com.soldev.fieldservice.TaskFormActivity;
import com.soldev.fieldservice.dataentity.DocumentClass;
import com.soldev.fieldservice.dataentity.ImageInfo;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.WebService;
import com.soldev.prod.mobileservice.R;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskImageHolderAdapter extends  RecyclerView.Adapter<TaskImageHolderAdapter.Holder> {
    private List<ImageInfo> imageInfos;
    private Context context;
    private String taskID;
    public TaskImageHolderAdapter(Context context,String taskID,List<ImageInfo> imageInfos) {
        this.imageInfos = imageInfos;
        this.context = context;
        this.taskID = taskID;
    }

    @NonNull
    @Override
    public TaskImageHolderAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskImageHolderAdapter.Holder holder, final int position) {
//        String fileName = imageInfos.get(position).getImageName();
//        Log.e("showimage",fileName);
        loadImageToView(position,holder.imgArea);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 removeItem(position);
            }
        });
        holder.imgArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("TASKID",taskID);
                bundle.putString("FILENAME",imageInfos.get(position).getImageName());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageInfos.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ImageView imgArea;
        ImageView imgDelete;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imgArea = itemView.findViewById(R.id.imgArea);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }

    public void loadImageToView(int position, ImageView imageView){
        File directory = context.getExternalFilesDir(taskID);
        Log.e("imageinfor",directory.toString()+":"+imageInfos.get(position).getImageName());
        File imgFile = new File(directory, imageInfos.get(position).getImageName());
        Log.e("getImage","name to load:"+imageInfos.get(position).getImageName());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            imageView.invalidate();
        } else {
            getImageFromServer(taskID,
                    AppConstant.IMAGE_CAT_GENERAL,
                    imageInfos.get(position).getImageName(),
                    imageView);
        }
    }

    public void removeItem(int position) {
        if(imageInfos.size()>=position) {
            imageInfos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void getImageFromServer(String docNo, String catName, final String fileName, final ImageView imageView ) {
        WebService webService = new WebService();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url =webService.getDocByNoURL()+docNo+","+catName+","+fileName + ",JPG";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Gson gson = new Gson();
                                JSONArray jsonArray = new JSONArray(response);
                                DocumentClass documentClass = gson.fromJson(jsonArray.getJSONObject(0).toString(),DocumentClass.class);
                                Log.e("getImage","result>"+documentClass.getApplicationNo());
                                byte[] byteArray = Base64.decode(documentClass.getDocumentContent(),Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                File directory = context.getExternalFilesDir(taskID);
                                File imgFile = new File(directory, fileName);
                                try (FileOutputStream out = new FileOutputStream(imgFile)) {
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                                    // PNG is a lossless format, the compression factor (100) is ignored
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                imageView.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                Log.e("getImage","Image holder error>"+e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json; charset=UTF-8");
                    params.put("authorization", "Bearer " + AppPreference.getAuthenToken(context));
                    return params;
                }

            };
            queue.add(stringRequest);
        } catch (Exception e){
            Log.e("update work record","error>"+e.toString());
        }

    }
}
