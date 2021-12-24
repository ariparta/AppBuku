package id.ari.appbuku;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.ari.appbuku.MainActivity;

public class LoginActivity extends AppCompatActivity {

    EditText TxUsername, TxPassword;
    Button BtnLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TxUsername = (EditText)findViewById(R.id.txUsername);
        TxPassword = (EditText)findViewById(R.id.txPassword);
        BtnLogin = (Button)findViewById(R.id.btnLogin);

        dbHelper = new DBHelper(this);

        TextView tvCreateAccount = (TextView)findViewById(R.id.tvCreateAccount);

        tvCreateAccount.setText(fromHtml("Tidak Memiliki Akun? " +
                "</font><font color='#3b5998'>Daftar Disini</font>"));
        tvCreateAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        BtnLogin.setOnClickListener(v -> {
            String username = TxUsername.getText().toString().trim();
            String password = TxPassword.getText().toString().trim();

            boolean res = dbHelper.checkUser(username,password);
            if(res){
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, ListBukuActivity.class));
            }else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Spanned fromHtml(String html){
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}