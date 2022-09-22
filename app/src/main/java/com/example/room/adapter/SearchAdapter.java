package com.example.room.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.room.R;
import com.example.room.databinding.AdapterSearchBinding;
import com.example.room.db.NetworkApp;
import com.example.room.model.LocalApp;
import com.example.room.util.GlobalUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<LocalApp> localApps;
    private List<NetworkApp> networkApps;
    private static final String TAG = "SearchAdapter";

    public SearchAdapter(Context context) {
        this.context = context;
        localApps = new ArrayList<>();
        networkApps = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLocalApps(List<LocalApp> localApps) {
        this.localApps.clear();
        this.localApps.addAll(localApps);
        notifyDataSetChanged();
        Log.e(TAG, "setLocalApps: " + localApps.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNetworkApps(List<NetworkApp> networkApps) {
        this.networkApps.clear();
        this.networkApps.addAll(networkApps);
        notifyDataSetChanged();
        Log.e(TAG, "setNetworkApps: " + networkApps.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSearchBinding binding = AdapterSearchBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < localApps.size()) {
            holder.name.setText(localApps.get(position).getLabel());
            holder.icon.setImageDrawable(localApps.get(position).getIcon());
            holder.open.setText("打开");
            holder.open.setBackground(context.getResources().getDrawable(R.drawable.shape_yellow));

            holder.open.setTextColor(context.getResources().getColor(R.color.color_open));
            holder.open.setOnClickListener(v -> {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(localApps.get(position).getPackageName());
                context.startActivity(intent);
            });
        } else {
            int position1 = position - localApps.size();
            String label = networkApps.get(position1).getLabel();
            String replace = label.replace("图标", "")
                    .replace("logo", "");
            holder.name.setText(replace);
            Glide.with(context).load(networkApps.get(position1).getUrl()).into(holder.icon);
            holder.open.setText("安装");
            holder.open.setBackground(context.getResources().getDrawable(R.drawable.shape_green));
            holder.open.setTextColor(context.getResources().getColor(R.color.color_download));
            holder.open.setOnClickListener(v -> {
                Toast.makeText(context, "软件不兼容", Toast.LENGTH_SHORT).show();
            });
        }
        holder.size.setText(
                new StringBuilder().append((int) (Math.random() * 10000 % 100 + 10))
                        .append("万 | ")
                        .append((int) (Math.random() * 10000 % 100 + 10))
                        .append("M")
        );
        holder.intro.setText(GlobalUtils.getIntroList().get((int) (Math.random() * 10000 % 100)));
        holder.itemView.setOnLongClickListener(v -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, new String[]{"隐藏", "取消"});
            ListView listView = new ListView(context);
            listView.setAdapter(adapter);
            BottomSheetDialog dialog = new BottomSheetDialog(context);
            dialog.setContentView(listView);
            dialog.show();
            listView.setOnItemClickListener((parent, view1, position12, id) -> {
                switch (position12) {
                    case 0:
                        remove(position);
                        break;
                    case 1:
                        break;
                    default:
                        break;
                }
                dialog.cancel();
            });
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return localApps.size() + networkApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        TextView open;
        TextView size;
        TextView intro;

        public ViewHolder(@NonNull AdapterSearchBinding binding) {
            super(binding.getRoot());
            icon = binding.icon;
            name = binding.name;
            open = binding.open;
            size = binding.size;
            intro = binding.intro;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void remove(int position) {
        if (position < localApps.size()) {
            GlobalUtils.getLocalApps().remove(localApps.get(position));
            GlobalUtils.addHideApps(localApps.get(position).getLabel());
            localApps.remove(position);
        } else {
            position = position - localApps.size();
            GlobalUtils.getNetworkApps().remove(networkApps.get(position));
            GlobalUtils.addHideApps(networkApps.get(position).getLabel());
            networkApps.remove(position);
        }
        notifyDataSetChanged();
    }
}
