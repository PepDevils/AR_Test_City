package pt.pepdevils.virtualbraga.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pt.pepdevils.virtualbraga.R;
import pt.pepdevils.virtualbraga.helper.LocationHelper;
import pt.pepdevils.virtualbraga.model.ARPoint;
import pt.pepdevils.virtualbraga.model.City;

/**
 * Created by Pedro Fonseca on 10/10/2017.
 */

public class AROverlayView extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private ArrayList<ARPoint> arPoints;
    private ArrayList<City> cities;


    public AROverlayView(Context context) {
        super(context);
        this.context = context;

        //Download City and points here
        arPoints = new ArrayList<ARPoint>() {{
            add(new ARPoint("Pep House", 41.559199, -8.403818, 0));
            add(new ARPoint("Ponto de Teste", 41.559222, -8.403888, 0));
            add(new ARPoint("Braga Parque", 41.5580735, -8.4058441, 0));
            add(new ARPoint("Azambujas House", 41.561128, -8.40956, 0));
            add(new ARPoint("Hospital", 41.567838, -8.399068, 0));
        }};

        City braga = new City("braga", arPoints);
        cities = new ArrayList<>();
        cities.add(braga);

    }


    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // sem a localização não desenhar nada
        if (currentLocation == null) {
            return;
        }

        //defenições para o desenho dos icones para cada localização, (ponto branco de raio 30px)
        final int radius = getResources().getInteger(R.integer.CameraWhiteDotRadius);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(getResources().getInteger(R.integer.CameraTextSize));


        //para cada cidade
        for (City city : cities) {

            for (int i = 0; i < city.getPoints().size(); i++) {
                float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
                float[] pointInECEF = LocationHelper.WSG84toECEF(city.getPoints().get(i).getLocation());
                float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

                float[] cameraCoordinateVector = new float[4];
                Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

                // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
                // if z > 0, the point will display on the opposite
                if (cameraCoordinateVector[2] < 0) {
                    // x e y paara circulo branco 0.5f 0.5f
                    float x = (0.5f + cameraCoordinateVector[0] / cameraCoordinateVector[3]) * canvas.getWidth();
                    float y = (0.5f - cameraCoordinateVector[1] / cameraCoordinateVector[3]) * canvas.getHeight();

                    //saber a distancia entre o meu local e o ponto
                    String distance = DistanceFromPoint(currentLocation, city.getPoints().get(i).getLocation());

                    //desenha o circulo e escreve o nome
                    canvas.drawCircle(x, y, radius, paint);
                    float textX = x - (30 * city.getPoints().get(i).getName().length() / 2);
                    float textY = y + 90;
                    float textYparagrafh = textY + 80;
                    float textXparagrafh = x - (30 * distance.length() / 2);

                    canvas.drawText(city.getPoints().get(i).getName(), textX, textY, paint);
                    canvas.drawText(distance, textXparagrafh, textYparagrafh, paint);


                    //para substituir o ponto branco por uma imagem

                    /*todo: ajustar a posiçao do icon no lugar exato do circulo branco
                    depois disso eliminar o ponto branco
                    fazer uma estrutura em que o ARPoint te uma categoria e essa categoria corresponde a um icon
                    */
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_delete); //ic_menu_help
                    // example: canvas.drawText(text, x, y, imgPaint);

                    float ffw = bitmap.getWidth();
                    float ffh = bitmap.getHeight();

                    ffw = ffw / 2; //relação n funciona, n centra o icone, perceber pk
                    ffw = ffw / 100;

                    ffh = ffh / 2;
                    ffh = ffh / 100;


                    Log.e("PEPE", "onDraw: " + "ffw: " + ffw + "  ffh: " + ffh);
                    Log.e("PEPE", "Density Canvas: " + getResources().getDisplayMetrics().density);
                    //x e y para bitmap de 96px xxhdpi(3)(480) - verificar varios dispositivos
                    //todo: perceber se é so neste dispositivo, ou qual a relação com estes numeros
                    ffw = 0.456f;
                    ffh = 0.47f;


                    x = (ffw + cameraCoordinateVector[0] / cameraCoordinateVector[3]) * canvas.getWidth();
                    y = (ffh - cameraCoordinateVector[1] / cameraCoordinateVector[3]) * canvas.getHeight();
                    canvas.drawBitmap(bitmap, x, y, paint);


                }
            }

        }

    }

    private String DistanceFromPoint(Location currentLocation, Location location) {

        //int Radius = 6371;// radius of earth in Km 6371
        int Radius = (int) getResources().getInteger(R.integer.radius_of_earth);
        String met = getResources().getString(R.string.m);

        StringBuilder tmp = new StringBuilder();
        String aux;

        double dLon = Math.toRadians(location.getLongitude() - currentLocation.getLongitude());
        double dLat = Math.toRadians(location.getLatitude() - currentLocation.getLatitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(currentLocation.getLatitude()))
                * Math.cos(Math.toRadians(location.getLatitude())) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double km = Radius * c;
        double m = km * 1000;

        aux = String.valueOf(((int) m));
        tmp.append(aux);
        tmp.append(" " + met);

        return tmp.toString();
    }

}
