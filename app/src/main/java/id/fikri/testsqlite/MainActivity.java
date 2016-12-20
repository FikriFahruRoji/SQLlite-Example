package id.fikri.testsqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper myDB;
    EditText tx_id, tx_name, tx_surname, tx_marks;
    Button btn_save, btn_list, btn_update, btn_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        tx_id = (EditText)findViewById(R.id.tx_id);
        tx_name = (EditText)findViewById(R.id.tx_name);
        tx_surname = (EditText)findViewById(R.id.tx_surname);
        tx_marks = (EditText)findViewById(R.id.tx_marks);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_list = (Button)findViewById(R.id.btn_students);
        btn_update = (Button)findViewById(R.id.btn_update);
        btn_delete = (Button)findViewById(R.id.btn_delete);

        btn_save.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save :
                boolean add = myDB.save_table_student(tx_name.getText().toString(), tx_surname.getText().toString(), tx_marks.getText().toString());
                if (add)
                    Toast.makeText(MainActivity.this, "Success Add Student", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Fails Add Student", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_students :
                Cursor students = myDB.list_table_student();
                if (students.getCount() == 0) {
                    alert_message("Message", "No data student found");
                    return;
                }
                //append data student to buffer
                StringBuffer buffer = new StringBuffer();
                while (students.moveToNext()) {
                    buffer.append("Id : " + students.getString(0) + "\n");
                    buffer.append("Name : " + students.getString(1) + "\n");
                    buffer.append("Surname : " + students.getString(2) + "\n");
                    buffer.append("Marks : " + students.getString(3) + "\n\n\n");
                }
                //show data student
                alert_message("List Students", buffer.toString());
                break;

            case R.id.btn_update :
                boolean update = myDB.update_table_student(tx_id.getText().toString(),
                        tx_name.getText().toString(),
                        tx_surname.getText().toString(),
                        tx_marks.getText().toString());
                if (update)
                    Toast.makeText(MainActivity.this, "Success update data Student", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Fails update data Student", Toast.LENGTH_LONG).show();
                break;

            case R.id.btn_delete:
                Integer delete = myDB.delete_student(tx_id.getText().toString());
                if (delete > 0)
                    Toast.makeText(MainActivity.this, "Success delete a Student", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Fails delete a Student", Toast.LENGTH_LONG).show();
                break;
        }
   }

    public void alert_message(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
