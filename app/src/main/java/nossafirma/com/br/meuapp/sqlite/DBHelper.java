package nossafirma.com.br.meuapp.sqlite;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nossafirma.com.br.meuapp.R;
import nossafirma.com.br.meuapp.SplashScreenActivity;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MeuApp.db";
    private static final int VERSAO_BANCO = 1;

    private Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSAO_BANCO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Criação da estrutura do banco de dados.
        executeScript(sqLiteDatabase, context, R.raw.sqlite_create);
        showLog("Banco criado!");
    }

    // Efetua leitura do script e se encontrar, pede execução.
    private void executeScript(SQLiteDatabase db, Context ctx, Integer scriptFileId) {

        Resources res = ctx.getResources();

        try {

            InputStream isScript = res.openRawResource(scriptFileId);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(isScript));

            executeScript(db, bufferedReader);

            bufferedReader.close();
            isScript.close();

        } catch (IOException e) {
            throw new RuntimeException("Não foi possivel ler o arquivo SQLite", e);
        }

    }

    // Executa o script.
    private void executeScript(SQLiteDatabase db, BufferedReader reader)
            throws IOException {

        String line;
        StringBuilder sb = new StringBuilder();

        // Efetua leitura enquanto houver linhas no script.
        while ((line = reader.readLine()) != null) {

            // Adiciona linha e pula.
            sb.append(line);
            sb.append(" \n ");

            // Verifica se é caracter fim da linha (;).
            if (line.endsWith(";")) {
                String script = sb.toString();
                showLog("Executando script: " + script);
                db.execSQL(script);
                sb = new StringBuilder();
            }

        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int older, int newer) {

        for (int i = older; i < newer; ++i) {

            String migrationFileName = String.format("from_%d_to_%d", i, (i + 1));
            showLog("Buscando pelo arquivo de migração: " + migrationFileName);

            int migrationFileId = context.getResources()
                    .getIdentifier(migrationFileName, "raw", context.getPackageName());
            showLog("Arquivo encontrado ID: " + migrationFileId);

            if (migrationFileId != 0) {

                showLog("Executando arquivo...");
                executeScript(sqLiteDatabase, context, migrationFileId);

            } else {
                showLog("Arquivo de migração não encontrado!!");
            }

        }
    }

    // Grava log.
    private void showLog(String msg) {
        Log.d(DBHelper.class.getSimpleName(), msg);
    }
}
