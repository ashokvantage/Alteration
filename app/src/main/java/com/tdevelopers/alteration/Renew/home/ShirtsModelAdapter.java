package com.tdevelopers.alteration.Renew.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.home.NewRenewActivity;

import java.util.List;

/**
 * Created by ADMIN on 11-Jan-18.
 */

public class ShirtsModelAdapter extends RecyclerView.Adapter<ShirtsModelAdapter.ShirtsModelViewHolder> {

    private List<ShirtItems> data;

    private ImageView itemImage;
    private ImageView itemImagegarments;

    public ShirtsModelAdapter(List<ShirtItems> data, ImageView itemImage,ImageView itemImagegarments) {
        this.data = data;
        this.itemImage = itemImage;
        this.itemImagegarments = itemImagegarments;
    }

    @Override
    public ShirtsModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShirtsModelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ShirtsModelViewHolder holder, int position) {

        try {
            final ShirtItems current = data.get(position);
            if (current != null) {

                if (current.fabric.name != null)
                    holder.name.setText(current.fabric.name);

                if (current.image != null)
                    Glide.with(holder.pic.getContext()).load(current.image).into(holder.pic);


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


                        for (ShirtItems item : data) {

                            if (item.equals(current))
                                continue;
                            item.itemFlag = false;
                        }
                        NewRenewActivity.txtcustomsubtvitle.setText(current.price);

                        if (current.itemFlag) {


                            itemImage.setVisibility(View.GONE);
                            itemImagegarments.setVisibility(View.GONE);
                            current.itemFlag = false;

                            //holder.container.setBackgroundDrawable(null);


                            holder.name.setTextColor(ContextCompat.getColor(view.getContext(), R.color.product_sub_name_color));
                            NewRenewActivity.use_imagePath.remove(current.image);
                            NewRenewActivity.display_imagePath.remove(current.image);
                            NewRenewActivity.name.remove(current.fabric.name);
                            NewRenewActivity.style.remove(current.shirt_body_style);
                            NewRenewActivity.id.remove(current.id);
                            NewRenewActivity.price.remove(current.price);
                            NewRenewActivity.Cat.remove("Shirt");

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
                            Glide.with(holder.itemView.getContext()).load(current.image).into(itemImage);
                            Glide.with(holder.itemView.getContext()).load(current.image).into(itemImagegarments);
                            current.itemFlag = true;
                            holder.name.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
                            if(NewRenewActivity.Cat.contains("Shirt"))
                            {
                                int a= NewRenewActivity.Cat.indexOf("Shirt");
                                NewRenewActivity.use_imagePath.set(a,current.image);
                                NewRenewActivity.display_imagePath.set(a,current.image);
                                NewRenewActivity.name.set(a,current.fabric.name);
                                NewRenewActivity.style.set(a,current.shirt_body_style);
                                NewRenewActivity.id.set(a,current.id);
                                NewRenewActivity.price.set(a,current.price);
                                NewRenewActivity.Cat.set(a,"Shirt");
                            }
                            else
                            {
                                NewRenewActivity.use_imagePath.add(current.image);
                                NewRenewActivity.display_imagePath.add(current.image);
                                NewRenewActivity.name.add(current.fabric.name);
                                NewRenewActivity.style.add(current.shirt_body_style);
                                NewRenewActivity.id.add(current.id);
                                NewRenewActivity.price.add(current.price);
                                NewRenewActivity.Cat.add("Shirt");
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

    class ShirtsModelViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView pic;

        public ShirtsModelViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            pic = (ImageView) itemView.findViewById(R.id.pic);
        }
    }
}
