package appkg.kg.shop3d.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import appkg.kg.shop3d.R;
import appkg.kg.shop3d.activity.DetailActivity;
import appkg.kg.shop3d.helper.ViewDialog;
import appkg.kg.shop3d.model.Models;

/**
 * Created by Suimonkul on 17.09.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PostHolder> {

    private static final String DOLLAR = " $";

    private Context context;
    private Activity activity;
    ViewDialog dialog;

    private ArrayList<Models> list;

    public RecyclerViewAdapter(ArrayList<Models> list) {
        this.list = list;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_model, parent, false);
        PostHolder postHolder = new PostHolder(view);
        context = parent.getContext();
        activity = (Activity) context;
        return postHolder;
    }

    @Override
    public void onBindViewHolder(PostHolder holder, final int position) {

        if (position < list.size()) {

            final Models models = list.get(position);

            holder.tvTitle.setText(models.getTitle());
            holder.tvOrder.setText(models.getOrder() + DOLLAR);
            holder.tvDescription.setText(models.getDescription());
            Picasso.with(context).load(models.getCover()).resize(640, 640).placeholder(R.drawable.ic_change_history_black_24dp).into(holder.imageModel);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);

                    intent.putExtra("models", models);

                    context.startActivity(intent);
                }
            });

//            holder.cardView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_UP:
//                            if (dialog != null) {
//                                dialog.dismiss();
//                            }
//                            break;
//                    }
//                    return false;
//                }
//            });
//            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    dialog = new ViewDialog(activity, models.getImage());
//                    dialog.show();
//                    return false;
//                }
//            });
        } else {
            holder.cardView.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PostHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tvTitle, tvDescription, tvOrder;
        ImageView imageModel;

        public PostHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tvTitle = (TextView) itemView.findViewById(R.id.text_title);
            tvOrder = (TextView) itemView.findViewById(R.id.text_order);
            tvDescription = (TextView) itemView.findViewById(R.id.text_description);
            imageModel = (ImageView) itemView.findViewById(R.id.image_model);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
