package com.example.todolist.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.todolist.Bean.Todos;
import com.example.todolist.Bean.User;
import com.example.todolist.DBHelper.MyDatabaseHelper;
import com.example.todolist.Interface.ItemTouchHelperAdapter;
import com.example.todolist.R;
import com.example.todolist.Bean.Tomato;
import com.example.todolist.Utils.BitmapUtils;
import com.example.todolist.Utils.SPUtils;
import com.example.todolist.Utils.ToDoUtils;
import com.example.todolist.Utils.ToastUtils;
import com.example.todolist.Utils.TomatoUtils;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * RecyclerView适配器
 */
public class ClockRecyclerViewAdapter extends RecyclerView.Adapter<ClockRecyclerViewAdapter.ViewHolder>
        implements ItemTouchHelperAdapter{

    private List<Tomato> tomatoList;
    private Context context;
    private MaterialDialog dialog;
    private int truePosition,itemPosition;
    private MyDatabaseHelper dbHelper;


    public ClockRecyclerViewAdapter(List<Tomato> tomato, Context context) {
        this.tomatoList = tomato;
        this.context=context;
    }


    //自定义ViewHolder类
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView clock_title;
        TextView work_time;
        ImageView clock_card_bg;




        public ViewHolder(View itemView) {
            super(itemView);
            clock_title = (TextView) itemView.findViewById(R.id.clock_title);
            work_time = (TextView) itemView.findViewById(R.id.work_time);
            clock_card_bg = (ImageView) itemView.findViewById(R.id.clock_card_bg);

        }


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_clock,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder ViewHolder, int i) {

        RequestOptions options2 =new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .signature(new ObjectKey(SPUtils.get(context,"head_signature","")))
                .placeholder(R.drawable.ic_img1);

        ViewHolder.clock_title.setText(tomatoList.get(tomatoList.size()-1-i).getTitle());
        ViewHolder.work_time.setText(tomatoList.get(tomatoList.size()-1-i).getWorkLength() + " 分钟");
        ViewHolder.clock_card_bg.setImageBitmap(BitmapUtils.readBitMap(context,tomatoList.get(tomatoList.size()-1-i).getImgId()));

    }

    @Override
    public int getItemCount() {
        return tomatoList.size();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(tomatoList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemRangeChanged(fromPosition,toPosition);
        return true;
    }

    public void removeItem(int position){
        truePosition = tomatoList.size()-1-position;
        itemPosition = position;
        popAlertDialog();
//        tomatoList.remove(tomatoList.size()-1-position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, tomatoList.size()-position);
    }

    private void popAlertDialog() {

        if (dialog == null) {

            dialog = new MaterialDialog(context);
            dialog.setMessage("确定删除？")
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Tomato tomato = tomatoList.get(truePosition);
                            String clockTitle = tomatoList.get(truePosition).getTitle();
                            dbHelper = new MyDatabaseHelper(context, "Data.db", null, 2);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            db.delete("Clock","clocktitle = ?",
                                    new String[]{clockTitle});

                            if (User.getCurrentUser(User.class) != null){
                                TomatoUtils.deleteNetTomato(context, tomato, new TomatoUtils.DeleteTomatoListener() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(int errorCord, String msg) {
                                        Log.i("ClockFragment", "msg ");
                                        Toasty.warning(context, msg, Toast.LENGTH_SHORT, true).show();
                                    }
                                });
                            }
                            tomatoList.remove(truePosition);
                            notifyItemRemoved(itemPosition);
                            notifyItemRangeChanged(itemPosition,tomatoList.size());
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new View.OnClickListener() {
                        public void onClick(View view) {
                            notifyItemChanged(itemPosition);
                            Log.i("sx", "item已刷新 ");
                            dialog.dismiss();
                        }
                    });

        }

        dialog.show();

    }


}
