package com.example.todolist.Fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.DBHelper.MyDatabaseHelper;
import com.example.todolist.Dao.ClockDao;
import com.example.todolist.R;
import com.example.todolist.SpacesItemDecoration;
import com.example.todolist.Activity.ClockActivity;
import com.example.todolist.Adapter.ClockRecyclerViewAdapter;
import com.example.todolist.Utils.ClockItemTouchHelperCallback;
//import com.example.todolist.Utils.NetWorkUtils;
import com.example.todolist.Utils.RecyclerItemClickListener;
import com.example.todolist.Bean.Tomato;
import com.example.todolist.Utils.SPUtils;
import com.example.todolist.Utils.TomatoUtils;
import com.example.todolist.Bean.User;
import com.example.todolist.Widget.ClockApplication;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class ClockFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private ClockRecyclerViewAdapter clockRecyclerViewAdapter;
    private List<Tomato> clockList = new ArrayList<>();
    private LinearLayoutManager layout;
    private User currentUser;
    private List<Tomato> localTomato;
    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback callback;
    private int workLength, shortBreak,longBreak,frequency;
    private String clockTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
//        if(NetWorkUtils.isNetworkConnected(getContext())) {
//            try{
//                if (User.getCurrentUser(User.class) != null){
//                    currentUser = BmobUser.getCurrentUser(User.class);
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clock, container, false);
        layout = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.clock_recycler_view);
        clockRecyclerViewAdapter = new ClockRecyclerViewAdapter(clockList, getActivity());
        recyclerView.setLayoutManager(layout);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0));
        recyclerView.setAdapter(clockRecyclerViewAdapter);

        // test alert
        new AlertDialog.Builder(getContext())
                .setTitle("标题")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("消息内容")
                .create().show();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                clockTitle = clockList.get(clockRecyclerViewAdapter.getItemCount()-1-position).getTitle();
                workLength = clockList.get(clockRecyclerViewAdapter.getItemCount()-1-position).getWorkLength();
                shortBreak = clockList.get(clockRecyclerViewAdapter.getItemCount()-1-position).getShortBreak();
                longBreak = clockList.get(clockRecyclerViewAdapter.getItemCount()-1-position).getLongBreak();
                frequency = clockList.get(clockRecyclerViewAdapter.getItemCount()-1-position).getFrequency();

                SPUtils.put(context,"pref_key_work_length", workLength);
                SPUtils.put(context,"pref_key_short_break", shortBreak);
                SPUtils.put(context,"pref_key_long_break", longBreak);
                SPUtils.put(context,"pref_key_long_break_frequency", frequency);

                Intent intent = new Intent(getActivity(), ClockActivity.class);
                intent.putExtra("clocktitle",clockTitle);
                intent.putExtra("workLength", workLength);
                intent.putExtra("shortBreak", shortBreak);
                intent.putExtra("longBreak", longBreak);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, final int position) {

            }
        }));

        callback = new ClockItemTouchHelperCallback(clockRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        setDbData();
//        setNetData();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDbData();
//        setNetData();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume(){
        setDbData();
        clockRecyclerViewAdapter.notifyDataSetChanged();
        super.onResume();

    }


    private void setDbData(){

        localTomato = TomatoUtils.getAllTomato(getContext());
        if (localTomato.size() > 0) {
            setListData(localTomato);
        }



    }

//    private void setNetData(){
//        if (NetWorkUtils.isNetworkConnected(getContext())){
//            if (currentUser != null){
//                // 获取网络，可能是换手机了，或者是没有添加过，或者是当前时间以后没有
//                TomatoUtils.getNetAllTomato(getContext(), currentUser, new TomatoUtils.GetTomatoCallBack() {
//                    @Override
//                    public void onSuccess(List<Tomato> tomato) {
//                        if (localTomato.size() < tomato.size()){
//                            new ClockDao(getContext()).clearAll();
//                            if (tomato != null){
//                                setListData(tomato);
//                                new ClockDao(getContext()).saveAll(tomato);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(int errorCode, String msg) {
//
//                    }
//                });
//            }
//        }
//    }


    /**
     * 设置list数据
     */
    private void setListData(List<Tomato> newList) {
        clockList.clear();
        clockList.addAll(newList);
        clockRecyclerViewAdapter.notifyDataSetChanged();
    }

}
