package com.tdevelopers.alteration.Renew.Collar;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.Renew.Collar.CollarResponses.CollarItem;
import com.tdevelopers.alteration.home.NewRenewActivity;

import java.util.List;

/**
 * Created by st185188 on 26/10/17.
 */

public class CollarModelsAdapter extends RecyclerView.Adapter<CollarModelsAdapter.ModelsViewHolder> {


    private List<CollarItem> data;
    private ImageView itemImage;
    private ImageView itemImagegarments;
//    RecyclerView modelsRvimage;
//    RecyclerView fabricRv;

    public CollarModelsAdapter(List<CollarItem> data, ImageView itemImage, ImageView itemImagegarments) {
        this.data = data;
//        this.modelsRvimage = modelsRvimage;
//        this.fabricRv = fabricRv;
        this.itemImage = itemImage;
        this.itemImagegarments = itemImagegarments;
    }

    @Override
    public ModelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ModelsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ModelsViewHolder holder, int position) {

        try {
            final CollarItem current = data.get(position);
            if (current != null) {

                if (current.fabric.name != null)
                    holder.name.setText(current.fabric.name);

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


                        for (CollarItem item : data) {

                            if (item.equals(current))
                                continue;
                            item.itemFlag = false;
                        }


                        if (current.itemFlag) {


                            Log.v("mtest", "if");

//                            modelsRvimage.setVisibility(View.GONE);
//                            fabricRv.setVisibility(View.VISIBLE);
//                            current.itemFlag = false;
                            itemImage.setVisibility(View.GONE);
                            itemImagegarments.setVisibility(View.GONE);
                            current.itemFlag = false;
                            //holder.container.setBackgroundDrawable(null);

                            holder.name.setTextColor(ContextCompat.getColor(view.getContext(), R.color.product_sub_name_color));

                            NewRenewActivity.use_imagePath.remove(current.use_image);
                            NewRenewActivity.display_imagePath.remove(current.display_image);
                            NewRenewActivity.name.remove(current.fabric.name);
                            NewRenewActivity.style.remove(current.collarStyle);
                            NewRenewActivity.id.remove(current.id);
                            NewRenewActivity.price.remove(current.price);
                            NewRenewActivity.Cat.remove("Collar");
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


//                            Log.v("mtest", "else");
//
//                            modelsRvimage.setVisibility(View.VISIBLE);
//                            fabricRv.setVisibility(View.GONE);
//                            modelsRvimage.setAdapter(new CollarFabricAdapter(current.options, itemImage, itemImagegarments));
//
//                            current.itemFlag = true;
                            itemImage.setVisibility(View.VISIBLE);
                            itemImagegarments.setVisibility(View.VISIBLE);
                            Glide.with(holder.itemView.getContext()).load(current.use_image).into(itemImage);
                            Glide.with(holder.itemView.getContext()).load(current.use_image).into(itemImagegarments);
                            current.itemFlag = true;
                            holder.name.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
                            if(NewRenewActivity.Cat.contains("Collar"))
                            {
                                int a= NewRenewActivity.Cat.indexOf("Collar");
                                NewRenewActivity.use_imagePath.set(a,current.use_image);
                                NewRenewActivity.display_imagePath.set(a,current.display_image);
                                NewRenewActivity.name.set(a,current.fabric.name);
                                NewRenewActivity.style.set(a,current.collarStyle);
                                NewRenewActivity.id.set(a,current.id);
                                NewRenewActivity.price.set(a,current.price);
                                NewRenewActivity.Cat.set(a,"Collar");
                            }
                            else
                            {
                                NewRenewActivity.use_imagePath.add(current.use_image);
                                NewRenewActivity.display_imagePath.add(current.display_image);
                                NewRenewActivity.name.add(current.fabric.name);
                                NewRenewActivity.style.add(current.collarStyle);
                                NewRenewActivity.id.add(current.id);
                                NewRenewActivity.price.add(current.price);
                                NewRenewActivity.Cat.add("Collar");
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

    class ModelsViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView pic;

        public ModelsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            pic = (ImageView) itemView.findViewById(R.id.pic);
        }
    }
}
