package com.example.launcher.adapter;

import static com.example.launcher.utils.AppUtils.dpToPx;
import static com.example.launcher.utils.AppUtils.pxToDp;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.launcher.R;
import com.example.launcher.constants.AppInfo;
import java.util.List;

/**
 * Adapter class for listing Launcher apps.
 * item_row_view_list is a single xml file containing a Container
 * and it will act as a single base for all the different types of lists.
 */

public class AppsDrawerAdapter extends RecyclerView.Adapter<AppsDrawerAdapter.ViewHolder> {

    private Context context = null;
    private List<AppInfo> apps = null;

    public AppsDrawerAdapter(Context context, List<AppInfo> apps) {
        this.context = context;
        this.apps = apps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_view_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AppInfo appInfo = apps.get(position);

        holder.textView.setText(appInfo.getLabel());
        holder.imageView.setImageDrawable(appInfo.getIcon());

        // launching the respective Launcher apps.
        holder.constraintLayout.setOnClickListener(view -> context.startActivity(context.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName().toString())));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private ConstraintLayout cl_inter;
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            constraintLayout = setListItemParameters(itemView);
            textView = setTextParameters();
            imageView = setIconParameters();
            constraintLayout.addView(imageView);
            constraintLayout.addView(textView);
        }

        private ConstraintLayout setListItemParameters(View itemView){ // Setting parameters for Ripple effect(Feedback)
            constraintLayout = itemView.findViewById(R.id.container);
            TypedValue rippleFeedback = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, rippleFeedback, true);
            constraintLayout.setBackgroundResource(rippleFeedback.resourceId);
            return constraintLayout;
        }

        private TextView setTextParameters(){ //Setting App Name parameters.
            textView = new TextView(context);
            ConstraintLayout.LayoutParams tvParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT);
            textView.setId(R.id.tv_label);
            tvParams.topToTop = R.id.iv_icon;
            tvParams.bottomToBottom = R.id.iv_icon;
            tvParams.startToEnd = R.id.iv_icon;
            tvParams.setMargins(dpToPx(context, (int) context.getResources().getDimension(R.dimen.spacing_quarter_filler)),  0, 0, 0);
            textView.setLayoutParams(tvParams);
            textView.setTextColor(ContextCompat.getColor(context, R.color.black));
            textView.setTextSize(pxToDp(context, (int) context.getResources().getDimension(R.dimen.text_normal)));
            return textView;
        }

        private ImageView setIconParameters(){ //Setting icon parameters.
            imageView = new ImageView(context);
            imageView.setId(R.id.iv_icon);
            ConstraintLayout.LayoutParams ivParams = new ConstraintLayout.LayoutParams(dpToPx(context, (int) context.getResources().getDimension(R.dimen.spacing_full)),
            dpToPx(context, (int) context.getResources().getDimension(R.dimen.spacing_full)));
            ivParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            ivParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            ivParams.setMargins((int) context.getResources().getDimension(R.dimen.spacing_quarter_filler), (int) context.getResources().getDimension(R.dimen.spacing_quarter_filler), 0, (int) context.getResources().getDimension(R.dimen.spacing_quarter_filler));
            imageView.setLayoutParams(ivParams);
            return imageView;
        }
    }
}

