package com.tdevelopers.alteration.Renew.Button;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.Renew.Button.ButtonResponses.ButtonItem;
import com.tdevelopers.alteration.home.NewRenewActivity;

import java.util.List;

/**
 * Created by st185188 on 30/10/17.
 */


public class ButtonsModelsAdapter extends RecyclerView.Adapter<ButtonsModelsAdapter.ButtonModelsViewHolder> {

    private List<ButtonItem> data;

    private ImageView itemImage;

    private ImageView itemImagegarments;
    public ButtonsModelsAdapter(List<ButtonItem> data, ImageView itemImage,ImageView itemImagegarments) {
        this.data = data;
        this.itemImage = itemImage;
        this.itemImagegarments = itemImagegarments;
    }

    @Override
    public ButtonModelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ButtonModelsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ButtonModelsViewHolder holder, int position) {

        try {
            final ButtonItem current = data.get(position);
            if (current != null) {

                if (current.name != null)
                    holder.name.setText(current.name);

                if (current.display_image != null)
                    Glide.with(holder.pic.getContext()).load(current.display_image).into(holder.pic);


                if (current.itemFlag) {


                    holder.name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary));
                    //holder.container.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.selected_drawable));
                } else {
                    // holder.container.setBackgroundDrawable(null);

                    holder.name.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.product_sub_name_color));
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        for (ButtonItem item : data) {

                            if (item.equals(current)) {

                                continue;
                            }
                            item.itemFlag = false;
                        }


                        if (current.itemFlag) {


                            itemImage.setVisibility(View.GONE);
                            itemImagegarments.setVisibility(View.GONE);
                            current.itemFlag = false;

                            //holder.container.setBackgroundDrawable(null);

                            holder.name.setTextColor(ContextCompat.getColor(view.getContext(), R.color.product_sub_name_color));
                            NewRenewActivity.use_imagePath.remove(current.use_image);
                            NewRenewActivity.display_imagePath.remove(current.display_image);
                            NewRenewActivity.name.remove(current.name);
                            NewRenewActivity.style.remove(current.name);
                            NewRenewActivity.id.remove(current.id);
                            NewRenewActivity.price.remove("10");
                            NewRenewActivity.Cat.remove("Button");

                            NewRenewActivity.total_price=0.0;
                            for (int i = 0; i < NewRenewActivity.price.size(); i++) {
                                NewRenewActivity.total_price = NewRenewActivity.total_price + Double.parseDouble(NewRenewActivity.price.get(i));

                            }
                            NewRenewActivity.total_price = NewRenewActivity.total_price + NewRenewActivity.additioncharge;
                            NewRenewActivity.txtprice.setText(""+NewRenewActivity.total_price);

                            NewRenewActivity.description = "";
                            if (NewRenewActivity.name.size() > 2) {
                                for (int i = 0; i < 2; i++) {
                                    if (i == 0)
                                        NewRenewActivity.description = NewRenewActivity.name.get(i);
                                    else
                                        NewRenewActivity.description = NewRenewActivity.description + "," + NewRenewActivity.name.get(i);
                                }
                            } else {
                                for (int i = 0; i < NewRenewActivity.name.size(); i++) {
                                    if (i == 0)
                                        NewRenewActivity.description = NewRenewActivity.name.get(i);
                                    else
                                        NewRenewActivity.description = NewRenewActivity.description + "," + NewRenewActivity.name.get(i);

                                }
                            }
                            if (NewRenewActivity.name.size() > 2) {
                                NewRenewActivity.txtdes.setText(NewRenewActivity.description + " & " + (NewRenewActivity.name.size() - 2) + " more");
                            } else {
                                NewRenewActivity.txtdes.setText(NewRenewActivity.description);
                            }
                        } else {


                            itemImage.setVisibility(View.VISIBLE);
                            itemImagegarments.setVisibility(View.VISIBLE);
                            Glide.with(holder.itemView.getContext()).load(current.use_image).into(itemImage);
                            Glide.with(holder.itemView.getContext()).load(current.use_image).into(itemImagegarments);
                            current.itemFlag = true;
                            //  holder.container.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.selected_drawable));

                            holder.name.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
                            if(NewRenewActivity.Cat.contains("Button"))
                            {
                                int a= NewRenewActivity.Cat.indexOf("Button");
                                NewRenewActivity.use_imagePath.set(a,current.use_image);
                                NewRenewActivity.display_imagePath.set(a,current.display_image);
                                NewRenewActivity.name.set(a,current.name);
                                NewRenewActivity.style.set(a,current.name);
                                NewRenewActivity.id.set(a,current.id);
                                NewRenewActivity.price.set(a,"10");
                                NewRenewActivity.Cat.set(a,"Button");
                            }
                            else
                            {
                                NewRenewActivity.use_imagePath.add(current.use_image);
                                NewRenewActivity.display_imagePath.add(current.display_image);
                                NewRenewActivity.name.add(current.name);
                                NewRenewActivity.style.add(current.name);
                                NewRenewActivity.id.add(current.id);
                                NewRenewActivity.price.add("10");
                                NewRenewActivity.Cat.add("Button");
                            }
                            NewRenewActivity.total_price=0.0;
                            for (int i = 0; i < NewRenewActivity.price.size(); i++) {
                                NewRenewActivity.total_price = NewRenewActivity.total_price + Double.parseDouble(NewRenewActivity.price.get(i));

                            }
                            NewRenewActivity.total_price = NewRenewActivity.total_price + NewRenewActivity.additioncharge;
                            NewRenewActivity.txtprice.setText(""+NewRenewActivity.total_price);

                            NewRenewActivity.description = "";
                            if (NewRenewActivity.name.size() > 2) {
                                for (int i = 0; i < 2; i++) {
                                    if (i == 0)
                                        NewRenewActivity.description = NewRenewActivity.name.get(i);
                                    else
                                        NewRenewActivity.description = NewRenewActivity.description + "," + NewRenewActivity.name.get(i);
                                }
                            } else {
                                for (int i = 0; i < NewRenewActivity.name.size(); i++) {
                                    if (i == 0)
                                        NewRenewActivity.description = NewRenewActivity.name.get(i);
                                    else
                                        NewRenewActivity.description = NewRenewActivity.description + "," + NewRenewActivity.name.get(i);

                                }
                            }
                            if (NewRenewActivity.name.size() > 2) {
                                NewRenewActivity.txtdes.setText(NewRenewActivity.description + " & " + (NewRenewActivity.name.size() - 2) + " more");
                            } else {
                                NewRenewActivity.txtdes.setText(NewRenewActivity.description);
                            }
                        }
                        notifyDataSetChanged();

                    }
                });


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class ButtonModelsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView pic;

        public ButtonModelsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            pic = (ImageView) itemView.findViewById(R.id.pic);
        }
    }
}
