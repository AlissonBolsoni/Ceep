package br.com.alisson.ceep.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import br.com.alisson.ceep.model.Cor;
import br.com.alisson.ceep.model.Nota;

public class NotaDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ceep";
    private static final int VERSION = 1;
    private static final String TABELA_NOTAS = "Notas";
    public static final String ID = "id";
    private static final String TITULO = "titulo";
    private static final String DESCRICAO = "descricao";
    private static final String COR_ID = "corId";
    private static final String COR_HEXA = "corHexa";
    private static final String POSICAO = "posicao";
    private static final String DESATIVADO = "desativado";
    private static final int POSICAO_SEM_NADA_NO_BD = -1;
    private Context context;

    public NotaDAO(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA_NOTAS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITULO + " TEXT NOT NULL, " +
                DESCRICAO + " TEXT, " +
                COR_ID + " INT, " +
                COR_HEXA + " TEXT, " +
                POSICAO + " INT, " +
                DESATIVADO + " INT DEFAULT 0" +
                ");";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @NonNull
    private String baseSql() {
        return "SELECT * FROM " + TABELA_NOTAS + " WHERE " + DESATIVADO + " = " + Nota.ATIVO + " ORDER BY " + POSICAO + " DESC";
    }

    public ArrayList<Nota> todos() {
        SQLiteDatabase db = getReadableDatabase();

        String sql = baseSql();

        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Nota> notas = populaNotas(cursor);

        return notas;
    }


    @NonNull
    private ArrayList<Nota> populaNotas(Cursor cursor) {
        ArrayList<Nota> notas = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(TITULO));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(DESCRICAO));
                int corId = cursor.getInt(cursor.getColumnIndexOrThrow(COR_ID));
                String corExa = cursor.getString(cursor.getColumnIndexOrThrow(COR_HEXA));
                int posicao = cursor.getInt(cursor.getColumnIndexOrThrow(POSICAO));
                int desativado = cursor.getInt(cursor.getColumnIndexOrThrow(DESATIVADO));

                notas.add(new Nota(id, titulo, descricao, new Cor(corId, corExa), posicao, desativado));
            }
            cursor.close();
        }
        return notas;
    }

    private int ultimaPosicao() {
        SQLiteDatabase db = getReadableDatabase();

        String sql = baseSql() + " LIMIT 1";

        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Nota> notas = populaNotas(cursor);

        if (notas.size() == 0) {
            return POSICAO_SEM_NADA_NO_BD;
        }

        return notas.get(0).getPosicao();
    }

    public void insere(Nota nota) {
        int pos = ultimaPosicao();
        SQLiteDatabase db = getWritableDatabase();

        pos++;
        ContentValues cv = populaNotaCV(pos, nota);
        long id = db.insert(TABELA_NOTAS, null, cv);
        nota.setId((int) id);
    }

    @NonNull
    private ContentValues populaNotaCV(Integer pos, Nota nota) {
        ContentValues cv = new ContentValues();

        cv.put(TITULO, nota.getTitulo());
        cv.put(DESCRICAO, nota.getDescricao());
        cv.put(COR_ID, nota.getCor().getIdCor());
        cv.put(COR_HEXA, nota.getCor().getCorHexa());

        if (pos == null) {
            cv.put(ID, nota.getId());
            cv.put(POSICAO, nota.getPosicao());
            cv.put(DESATIVADO, nota.getDesativado());
        } else {
            cv.put(POSICAO, pos);
            cv.put(DESATIVADO, 0);
        }

        return cv;
    }

    public void altera(Nota nota) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = ID + " = " + nota.getId();

        ContentValues cv = populaNotaCV(null, nota);
        db.update(TABELA_NOTAS, cv, sql, null);
    }

    public void remove(Nota nota) {
        nota.desativa();
        altera(nota);
    }

    public void troca(Nota notaInicio, Nota notaFim) {

        notaInicio.setPosicao(notaFim.getPosicao());
        altera(notaInicio);

        notaFim.setPosicao(notaInicio.getPosicao());
        altera(notaFim);

    }
}
