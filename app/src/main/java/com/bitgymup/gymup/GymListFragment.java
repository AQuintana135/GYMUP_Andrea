package com.bitgymup.gymup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.bitgymup.gymup.Gym;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GymListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GymListFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerGym;
    ArrayList<Gym> listGym;

    ProgressDialog progress;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public GymListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GymListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GymListFragment newInstance(String param1, String param2) {
        GymListFragment fragment = new GymListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_gym_list, container, false);
        View view = inflater.inflate(R.layout.activity_gym_list, container, false);

        listGym = new ArrayList<>();

        recyclerGym= (RecyclerView) view.findViewById(R.id.recicler);
        recyclerGym.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerGym.setHasFixedSize(true);

        request = Volley.newRequestQueue(getContext());

        cargarWebService();

        return view;
    }

    private void cargarWebService() {
        progress = new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();

        String url="http://gymup.zonahosting.net/gymphp/getGimnasiosWS.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
        //VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Gym gym = null;

        JSONArray json=response.optJSONArray("detail");

        try {

            for (int i=0;i<json.length();i++){
             //   gym = new Gym();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

               // gym.setName(jsonObject.optString("name"));
              //  gym.setEmail(jsonObject.optString("email"));
              //  gym.setPhoneNumber(jsonObject.optString("rut"));
                gym = new Gym(jsonObject.optString("id"), jsonObject.optString("name"), jsonObject.optString("email"), jsonObject.optString("phone"),jsonObject.optString("mobile"), jsonObject.optString("rut"));
                listGym.add(gym);
            }
            progress.hide();
            GymListAdapter adapter = new GymListAdapter(listGym, getContext(), new GymListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Gym item) {

                }
            });
            recyclerGym.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            progress.hide();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}