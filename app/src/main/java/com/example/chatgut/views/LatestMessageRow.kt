package com.example.chatgut.views

import android.graphics.Typeface
import com.example.chatgut.R
import com.example.chatgut.models.ChatMessage
import com.example.chatgut.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<GroupieViewHolder>() {

    var chatPartnerUser: User? = null

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.textView_username_latest_messages.text = chatPartnerUser?.username
        //viewHolder.itemView.textView_message_latest_messages.text = chatMessage.text

        val chatPartnerId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatPartnerId = chatMessage.toId
        } else {
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(User::class.java)

                viewHolder.itemView.textView_username_latest_messages.text = chatPartnerUser?.username
                viewHolder.itemView.textView_message_latest_messages.text = chatMessage.text

                val targetImageView = viewHolder.itemView.imageView_profilePic_latest_messages
                Picasso.get().load(chatPartnerUser?.profileImageURL).into(targetImageView)

                viewHolder.itemView.textView_message_latest_messages.text = chatMessage.text
                //viewHolder.itemView.textView_message_latest_messages.setTypeface(null, Typeface.BOLD)



                // have latest message bold if not seen
                if(!chatMessage.messageSeen){



                    //So in terms of ideas besides what I did, you could also do something like check
                    //last time recipients checked messages and then if time is less than latest
                    //message then keep in a bold. So you'll have to get a bit clever, remember
                    //chatMessage already has a timestamp.

                    //val textView = viewHolder.itemView.latestMessageTextView

                } else{

                }
            }



            override fun onCancelled(p0: DatabaseError) {

            }
        })



    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}