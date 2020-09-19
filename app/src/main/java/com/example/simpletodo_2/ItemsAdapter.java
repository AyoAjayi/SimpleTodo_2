package com.example.simpletodo_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//The adapter is responsible for taking data  at a particular position  and putting it into a view holder. Make adapter extend RecyclerView.Adapter
// The recycler view adapter is parameterized by the view holder so you have to make the view holder first.
// Implement required methods
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    //MainActivity.java will construct our Adapter class
    //Hit command N to go to generate and then generate a constructor
    //Constructor takes in a list of strings called items

    //Interface for onLongClickListener that communicates to MainActivity.Java

    public interface OnLongClickListener {
        //We pass in position so that MainActivity knows the position where we did the long press so the adapter can know.
        void onItemLongClicked(int position);
    }
    List<String> items;

    /// //Remove static from OnLongClickListener
    OnLongClickListener longClickListener;

    // Update ItemsAdapter constructor to now take in OnLongClickListener to delete data
    // Make a new member variable for the OnLongClickListener

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        //Setting the member variable to the variable passed into the constructor
        this.items = items;

        // // Remove ItemAdapter.longClickLister and do this instead
        this.longClickListener = longClickListener;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view and wrap it inside the view holder.
        // Use layout inflator to inflate a view. You need a context, you can go ahead and get context from the viewGroup parent in the onCreateViewHolder method.
        // Then pass in the xml file of the view you are creating (Use built in android resource file which is called simple_list_item_1) for the first parameter.
        // Then pass in root which is the parent and false for attach to root because we want to attach the view and not the root, for the next parameters.
        // This will return a View to us
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //wrap it inside a view holder and return it.
        return new ViewHolder(todoView);
    }

    //Binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab the item at the position.
        String item = items.get(position);
        //Bind the item into the specified view holder.
        holder.bind(item);
        //We will write this method inside of the view holder class. Hit option enter.


    }

    //Tells recycler view number of items in the lise
    @Override
    public int getItemCount() {
        return items.size();
    }

    // First define the view holder. Container to access views that represent each row in the list.
    // ViewHolder has to extend RecyclerView.ViewHolder
    // Create a constructor for the class

    // Should not be a static class

    class ViewHolder extends RecyclerView.ViewHolder{
        // Our view which is simple_list_item_1 which is built in consists of an id known as text1. Use this to reference to the item1 textview

        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);

        }

        //Update the view inside of the view holder with this datax
        public void bind(String item) {
            tvItem.setText(item);
            //Set a long click listener, to delete an item that someone long presses

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //We notify the listener of the position of the data that was long pressed.
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    // We need to create an interface in the items adapter that mainActivity will implement. Do that at the top.
                    return true;
                }
            });
        }
    }
}
