package com.example.vecinosvigilantes;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vecinosvigilantes.vecino.aplicacion.logica.AdapterAlertas;
import com.example.vecinosvigilantes.vecino.aplicacion.logica.DialogCambiarNombre;
import com.example.vecinosvigilantes.vecino.dominio.AlertaClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class PerfilFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference referenciaAlertas;
    private DatabaseReference referenciaGrupo;
    private String idGrupo;
    private StorageReference storageReference;

    private static final int GALLERY_INTENT = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private ImageView fotoPerfil;
    private RecyclerView recyclerAlertas;
    ArrayList<AlertaClass> listaAlertas;
    private AdapterAlertas adapterAlertas;
    String idUsuarioLog;

    String nombreUsuario;
    ImageButton btnDescargarAlertas;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        TextView txtUsuario =  root.findViewById(R.id.txtPerfil);
        TextView nomUsuarioLog = root.findViewById(R.id.nombreUsuarioLog);
        ImageButton btnCerrarSesion = (ImageButton) root.findViewById(R.id.btnCerrarSesion);
        ImageButton btnCambiarNombre = (ImageButton) root.findViewById(R.id.btnCambiarNombre);
        ImageButton btnCambiarFoto = (ImageButton) root.findViewById(R.id.btnCambiarFoto);
        ImageButton btnEliminarPerfil = (ImageButton) root.findViewById(R.id.btnEliminarPerfil);
        btnDescargarAlertas = (ImageButton) root.findViewById(R.id.btnDescargas);



        recyclerAlertas = (RecyclerView) root.findViewById(R.id.recyclerAlertasUsuario);
        recyclerAlertas.setHasFixedSize(true);
        recyclerAlertas.setLayoutManager(new LinearLayoutManager(root.getContext()));

        listaAlertas = new ArrayList<>();
        adapterAlertas = new AdapterAlertas(root.getContext(),listaAlertas);
        recyclerAlertas.setAdapter(adapterAlertas);

        fotoPerfil = (ImageView) root.findViewById(R.id.imagePerfil);



        String usuarioLog = firebaseAuth.getCurrentUser().getEmail();
        idUsuarioLog = firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog);
        referenciaAlertas = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuarioLog).child("AlertasGeneradas");

        txtUsuario.setText("Usuario logeado " + usuarioLog);

        cargarInfoUsuario(fotoPerfil, nomUsuarioLog);

        referenciaAlertas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AlertaClass alerta = dataSnapshot.getValue(AlertaClass.class);
                    listaAlertas.add(alerta);
                }
                adapterAlertas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       ActivityCompat.requestPermissions(getActivity(), new String[]{
               Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
       generarPDF();



        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Toast.makeText(getContext(), "Sesion Cerrada con exito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        btnCambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogo();

            }
        });

        btnCambiarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }
        });





      /*  btnEliminarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Desea eliminar su cuenta")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });*/
        btnEliminarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAlertDialog();
            }
        });



        // Inflate the layout for this fragment
        return root;

    }

    private void getAlertDialog(){
        referenciaGrupo = FirebaseDatabase.getInstance().getReference("Grupos").child(idGrupo).child("miembros").child(idUsuarioLog);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_authentication,null);

        EditText email = dialogView.findViewById(R.id.dialogEmail);
        EditText password = dialogView.findViewById(R.id.dialogPassword);
        Button reatenticar = dialogView.findViewById(R.id.btnReautenticar);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Eliminar cuenta");
        alertDialog.show();

        reatenticar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(email.getText().toString(),password.getText().toString());

                if(user != null){
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       user.delete()
                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        databaseReference.removeValue();
                                                        referenciaGrupo.removeValue();
                                                        alertDialog.dismiss();
                                                        Toast.makeText(getContext(),"Usuario eliminado",Toast.LENGTH_SHORT).show();
                                                        FirebaseAuth.getInstance().signOut();
                                                        startActivity(new Intent(getContext(), IniciarSesionActivity.class));
                                                        getActivity().finish();
                                                    }else{
                                                        alertDialog.dismiss();
                                                        Toast.makeText(getContext(),"Error al autenticar",Toast.LENGTH_SHORT).show();
                                                    }
                                                   }
                                               });

                                   }else{
                                       alertDialog.dismiss();
                                       Toast.makeText(getContext(),"Error al autenticar",Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                }

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
                Uri uri = data.getData();

                StorageReference rutaFotos = storageReference.child("fotosPerfil/").child(uri.getLastPathSegment());

                rutaFotos.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Foto cambiada", Toast.LENGTH_SHORT).show();
                        storageReference.child("fotosPerfil").child(uri.getLastPathSegment()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri ppic = uri;
                                String newppuser = ppic.toString();
                                HashMap map = new HashMap();
                                map.put("pp", newppuser);
                                databaseReference.updateChildren(map);
                                Glide.with(getContext()).load(newppuser).into(fotoPerfil);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al cambiar la imagen", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    public void cargarInfoUsuario(ImageView fotoPerfil, TextView nombreUsLog){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String pp = snapshot.child("pp").getValue(String.class);
                    nombreUsuario = snapshot.child("nombre").getValue(String.class);
                    idGrupo = snapshot.child("id_grupo").getValue(String.class);
                    Glide.with(getActivity()).load(pp).into(fotoPerfil);
                    nombreUsLog.setText(nombreUsuario);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void abrirDialogo(){
        DialogCambiarNombre dialog = new DialogCambiarNombre();
        dialog.show(getParentFragmentManager(),"Cambiar nombre");
    }

    public void generarPDF() {
        btnDescargarAlertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaAlertas.isEmpty()){
                    Toast.makeText(getContext(), "Necesitas taner alertas para Descargarlas", Toast.LENGTH_SHORT).show();
                }else {
                    PdfDocument pdfDocument = new PdfDocument();
                    int pageHeight = 1120;
                    int pagewidth = 900;
                    int x = 10;
                    final int[] yy = {300};


                    Paint titulo = new Paint();
                    Paint txtAlerta = new Paint();

                    PdfDocument.PageInfo infoPag = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
                    PdfDocument.Page pagina = pdfDocument.startPage(infoPag);

                    Canvas canvas = pagina.getCanvas();

                    titulo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                    titulo.setTextSize(30);
                    titulo.setColor(ContextCompat.getColor(getContext(), R.color.light_blue_A400));

                    txtAlerta.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                    txtAlerta.setTextSize(20);
                    txtAlerta.setColor(ContextCompat.getColor(getContext(), R.color.black));

                    canvas.drawText("Alertas de " + nombreUsuario, 209, 100, titulo);

                    listaAlertas.forEach(alerta -> {
                        canvas.drawText(alerta.toString(), x, yy[0], txtAlerta);
                        yy[0] = yy[0] - 50;
                    });

                    pdfDocument.finishPage(pagina);

                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/MisAlertas.pdf");
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            pdfDocument.writeTo(Files.newOutputStream(file.toPath()));
                        }
                        Toast.makeText(getContext(), "PDF generado en Descargas", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pdfDocument.close();
                }
            }
        });
    }

}