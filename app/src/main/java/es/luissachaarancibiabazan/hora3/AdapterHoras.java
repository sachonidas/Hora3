package es.luissachaarancibiabazan.hora3;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdapterHoras extends RecyclerView.Adapter<AdapterHoras.AdapterViewHolder> {

    private List<Hora> horaList;
    AdminSqlite adminSqlite;
    private final LayoutInflater mInflater = null;

    public AdapterHoras(List<Hora> horaList){
        this.horaList = horaList;

    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        TextView txtHoras, txtFecha, txtDes;
        LinearLayout linearLayout;

        public AdapterViewHolder(View itemView){
            super(itemView);
            //linearLayout = itemView.findViewById(R.id.linear);
            txtHoras = itemView.findViewById(R.id.textView1);
            txtFecha = itemView.findViewById(R.id.textView2);
            txtDes = itemView.findViewById(R.id.textView3);
        }
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        AdapterViewHolder holder = new AdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.txtHoras.setText("Horas: " + horaList.get(position).getNumeroHoras());
        holder.txtFecha.setText("Fecha Horas:  " + horaList.get(position).getFechaHora());
        if (horaList.get(position).getDescripcion() != null){
            holder.txtDes.setText("Descripción:  " + horaList.get(position).getDescripcion());
        }else{
            holder.txtDes.setText("Descripción:  " );
        }
        //String descSql = horaList.get(position).getDescripcion();
        //notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return horaList.size();
    }




}
