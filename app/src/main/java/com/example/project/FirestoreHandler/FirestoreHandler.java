package com.example.project.FirestoreHandler;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.project.ui.client_slove.ClientSolveFragment;
import com.example.project.ui.factory_ordered.FactoryOrderedFragment;
import com.example.project.ui.factory_ordering.FactoryOrderingFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FirestoreHandler {

    private static String TAG = "FirestoreHandler";
    private static boolean check = true;
    private static List<Order> orderList;

    public static String upload(String collection, Map<String, Object> data){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newRef = db.collection(collection).document();
        newRef.set(data);
        return newRef.getId();
    }

    public static void upload(String collection, String document, Map<String, Object> data){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.v(TAG, collection + document);
        db.collection(collection)
                .document(document)
                .set(data);
    }

    public static void getOrderOfUser(String user, final ClientSolveFragment c){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        orderList = new ArrayList<Order>();
        db.collection("projectOrder")
                .whereEqualTo("user", user)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> orderData = document.getData();
                                Order order = new Order();
                                order.object = orderData.get("項目").toString();
                                order.material = orderData.get("材料").toString();
                                order.size = orderData.get("尺寸").toString();
                                order.other = orderData.get("備註").toString();
                                order.id = document.getId();
                                order.status = orderData.get("status").toString();
                                order.price = orderData.get("price").toString();
                                orderList.add(order);
                            }
                            Log.v(TAG, orderList.toString());
                            c.setRecycleView(c.root, orderList);

                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return;
    }

    public static void getAllOrder(final FactoryOrderingFragment f){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        orderList = new ArrayList<Order>();
        db.collection("projectOrder")
                .whereEqualTo("status", "ordering")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> orderData = document.getData();
                                Order order = new Order();
                                order.object = orderData.get("項目").toString();
                                order.material = orderData.get("材料").toString();
                                order.size = orderData.get("尺寸").toString();
                                order.other = orderData.get("備註").toString();
                                order.id = document.getId();
                                order.lowerprice = orderData.get("lowerprice").toString();
                                orderList.add(order);
                            }
                            Log.v(TAG, orderList.toString());
                            f.setRecycleView(f.root, orderList);

                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return;
    }

    public static void getOrdered(final FactoryOrderedFragment f, String factoryName){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        orderList = new ArrayList<Order>();
        db.collection("projectOrder")
                .whereEqualTo("status", factoryName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> orderData = document.getData();
                                Order order = new Order();
                                order.object = orderData.get("項目").toString();
                                order.material = orderData.get("材料").toString();
                                order.size = orderData.get("尺寸").toString();
                                order.other = orderData.get("備註").toString();
                                order.id = document.getId();
                                order.user = orderData.get("user").toString().substring(1);
                                orderList.add(order);
                            }
                            Log.v(TAG, orderList.toString());
                            f.setRecycleView(f.root, orderList);

                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public static void update(String collection, String document, String field, String data){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).document(document).update(field, data);
    }

    public static void solveOrder(final String documentid, final TextView t){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orderResult")
                .document(documentid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Map<String, Object> orderData = document.getData();
                                int max = 0;
                                String maxFactory = "NoFactory";
                                for (Map.Entry<String, Object> entry : orderData.entrySet()){
                                    if(entry.getKey().equals("id"))
                                        continue;
                                    else{
                                        if(Integer.valueOf(entry.getValue().toString()) > max){
                                            max = Integer.valueOf(entry.getValue().toString());
                                            maxFactory = entry.getKey().substring(1);
                                        }
                                    }
                                }
                                t.setText("廠商: " + maxFactory);
                                update("projectOrder", documentid, "status", maxFactory);
                                update("projectOrder", documentid, "price", String.valueOf(max));

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    public static void delete(String collection, String document){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).document(document).delete();
    }
}
