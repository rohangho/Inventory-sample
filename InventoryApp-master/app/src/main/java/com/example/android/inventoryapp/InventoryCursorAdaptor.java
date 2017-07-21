package com.example.android.inventoryapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

import static com.example.android.inventoryapp.R.id.price;

/**
 * Created by Nicholas on 5/24/2017.
 */

public class InventoryCursorAdaptor extends CursorAdapter {
    /*int quantity;
    String invStock;
    TextView stockText;*/
    // Uri uri;

    /**
     * Constructs a new {@link InventoryCursorAdaptor}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdaptor(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the item data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current item can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find fields to populate in inflated template
        final TextView nameText = (TextView) view.findViewById(R.id.name);
        final TextView priceText = (TextView) view.findViewById(price);
        final TextView stockText = (TextView) view.findViewById(R.id.in_stock);
        final ImageView itemImage = (ImageView) view.findViewById(R.id.image);

        // Extract properties from cursor

        final String i = cursor.getString(cursor.getColumnIndex(InventoryEntry._ID));
        int name = cursor.getColumnIndex(InventoryEntry.Column_Item_Name);
        int price = cursor.getColumnIndex(InventoryEntry.Column_Item_Price);
        int quantity = cursor.getColumnIndex(InventoryEntry.Column_Item_Quantity);
        int image = cursor.getColumnIndex(InventoryEntry.Column_Item_Image);
        int currentquantity = cursor.getInt(quantity);
        final Uri uri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, Long.parseLong(i));

        String invName = cursor.getString(name);


        String invPrice = cursor.getString(price);


        String itemUri = cursor.getString(image);


        // Populate fields with extracted properties
        nameText.setText(invName);
        priceText.setText(invPrice);
        stockText.setText(String.valueOf(currentquantity));
        itemImage.setImageURI(Uri.parse(itemUri));


        //declare button and initialize it
        Button sellOne = (Button) view.findViewById(R.id.sell);
        sellOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int qty = Integer.parseInt(stockText.getText().toString());
                --qty;
                if (qty <= 0) {
                    qty = 0;
                }

                stockText.setText(String.valueOf(qty));


                if (qty >= 0) {
                    ContentResolver resolver = context.getContentResolver();
                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.Column_Item_Name, nameText.getText().toString());
                    values.put(InventoryEntry.Column_Item_Price, priceText.getText().toString());
                    values.put(InventoryEntry.Column_Item_Quantity, qty);


                    int rowsAffected = resolver.update(uri, values, null, null);


                }


                String newStock = String.valueOf(qty);
                if (qty == 0) {
                    newStock = "None Left";
                }
                stockText.setText(newStock);

            }
        });


    }
}