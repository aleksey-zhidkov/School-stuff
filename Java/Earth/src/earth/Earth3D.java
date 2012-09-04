package earth;

import java.io.*;
import java.applet.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.*;

import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.universe.*;


public class Earth3D extends Applet
{
  private SimpleUniverse u = null;
  private java.net.URL etexture;
  private java.net.URL mtexture;
  private java.net.URL bgImage;
  private BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
  private String confPath;

  // Параметры сцены задаваемые из файла
  float initScale; // Начальное приближение
  int bgPoly;  // количество полигонов в фоновой сфере
  float earthSize; // размер Земли
  int earthPoly; // количество полигонов Земли
  int earthSteps; // количество шагов Земли для полного оборота
  float earthSpeed; // задержка между шагами поворота Земли
  float moonSize; // размер Луны
  int moonPoly; // количество полигонов Луны
  int moonSteps; // количество шагов Луны для полного оборота
  float moonSpeed; // задержка между шагами вращения Луны
  float glcr; // красная составляющая цвета глобального освещения
  float glcg; // зелёная составляющая цвета глобального освещения
  float glcb; // синяя составляющая цвета глобального освещения
  float dlcr; // красная составляющая цвета направленного освещения
  float dlcg; // зелёная составляющая цвета направленного освещения
  float dlcb; // синяя составляющая цвета направленного освещения

  // инициализация апплета
  public void init()
  {
    // Устанавливаем параметры указанные в конфигурационном файле
    try
    {
      setParams();
    }
    catch (Exception ex)
    {
      System.out.println(ex.getMessage());
      System.exit(1);
    }

    setLayout(new BorderLayout());
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

    Canvas3D c = new Canvas3D(config);
    add("Center", c);

    // Создаём простую сцену и виртуальное пространство
    BranchGroup scene = createSceneGraph();
    u = new SimpleUniverse(c);

    // Создаём камеру
    ViewingPlatform viewingPlatform = u.getViewingPlatform();

    // Устанавливаем камеру так, чтобы была видна сцена
    viewingPlatform.setNominalViewingTransform();

    TransformGroup viewTrans = u.getViewingPlatform().getViewPlatformTransform();

    // Добовляем к сцене возможность вращения
    MouseRotate behavior1 = new MouseRotate(viewTrans);
    scene.addChild(behavior1);
    behavior1.setSchedulingBounds(bounds);

    // Добовляем к сцене возможность масштабирования
    MouseZoom behavior2 = new MouseZoom(viewTrans);
    scene.addChild(behavior2);
    behavior2.setSchedulingBounds(bounds);

    // Добовляем к сцене возможность перемещения
    MouseTranslate behavior3 = new MouseTranslate(viewTrans);
    scene.addChild(behavior3);
    behavior3.setSchedulingBounds(bounds);

    // Компилируем для оптимизации графа сцены и увелечения производительности
    scene.compile();

    // Добовляем сцену к виртуальному пространству
    u.addBranchGraph(scene);
    }

    // генерация сцены
    public BranchGroup createSceneGraph()
    {
      // Создаём корневой объект сцены
      BranchGroup objRoot = new BranchGroup();

      // Создаём Transformgroup для масштабирования объектов сцены
      TransformGroup objScale = new TransformGroup();
      Transform3D t3d = new Transform3D();
      t3d.setScale(initScale);
      objScale.setTransform(t3d);
      objRoot.addChild(objScale);

      TransformGroup bobjTrans = new TransformGroup();
      objScale.addChild(bobjTrans);
      Background bg = new Background();
      bg.setApplicationBounds(bounds);
      BranchGroup backGeoBranch = new BranchGroup();
      Sphere sphereObj = new Sphere(1.0f, Sphere.GENERATE_NORMALS |
                                Sphere.GENERATE_NORMALS_INWARD |
                                Sphere.GENERATE_TEXTURE_COORDS, bgPoly);
      Appearance backgroundApp = sphereObj.getAppearance();
      backGeoBranch.addChild(sphereObj);
      bg.setGeometry(backGeoBranch);
      bobjTrans.addChild(bg);

      TextureLoader tex = new TextureLoader(bgImage,
                                            new String("RGB"), this);
      if (tex != null)
          backgroundApp.setTexture(tex.getTexture());


      createLights(objRoot);

      // Создаём текстуру
      Appearance app = new Appearance();
      tex = new TextureLoader(etexture, this);
      app.setTexture(tex.getTexture());

      // Задаем свойства материала
      app.setMaterial(new Material(new Color3f(1.0f, 1.0f, 1.0f), new Color3f(0.0f, 0.0f, 0.0f),
                                   new Color3f(1.0f, 1.0f, 1.0f), new Color3f(0.0f, 0.0f, 0.0f),
                                   1.0f));
      TextureAttributes texAttr = new TextureAttributes();
      texAttr.setTextureMode(TextureAttributes.MODULATE);
      app.setTextureAttributes(texAttr);


      // Создаём и добовляем к сцене Землю
      Sphere earth = new Sphere(earthSize,Sphere.GENERATE_NORMALS |
                                     Sphere.GENERATE_TEXTURE_COORDS,
                                     earthPoly);
      earth.setAppearance(app);

      // Задём положение Земли
      Transform3D t = new Transform3D();
      TransformGroup objTrans = new TransformGroup(t);
      TransformGroup spinTg = new TransformGroup();
      spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
      spinTg.addChild(earth);

      // Задём вращение Земли
      Transform3D yAxis = new Transform3D();
      Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0, 0,earthSteps, 0, 0, 0, 0, 0);
      RotationInterpolator rotator =  new RotationInterpolator(rotationAlpha, spinTg, yAxis,
                                           0.0f, (float) Math.PI*earthSpeed);
      BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
      rotator.setSchedulingBounds(bounds);
      objTrans.addChild(rotator);
      objTrans.addChild(spinTg);
      objRoot.addChild(objTrans);

      // Задаём положение луны
      TransformGroup lRotTrans = new TransformGroup();
      lRotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      lRotTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
      objScale.addChild(lRotTrans);
      t = new Transform3D();
      Vector3d lPos =  new Vector3d(0.0, 0.0, 2.0);
      t.set(lPos);
      TransformGroup lTrans = new TransformGroup(t);
      lRotTrans.addChild(lTrans);

      // Создаём текстуру
      app = new Appearance();
      tex = new TextureLoader(mtexture, this);
      app.setTexture(tex.getTexture());

      // Задаем свойства материала
      app.setMaterial(new Material(new Color3f(1.0f, 1.0f, 1.0f), new Color3f(0.0f, 0.0f, 0.0f),
                                   new Color3f(1.0f, 1.0f, 1.0f), new Color3f(0.0f, 0.0f, 0.0f),
                                   1.0f));
      texAttr = new TextureAttributes();
      texAttr.setTextureMode(TextureAttributes.MODULATE);
      app.setTextureAttributes(texAttr);

      // Создаём луну, задаём её вид и добовляем к TransformGroup
      Sphere moon = new Sphere(moonSize,Sphere.GENERATE_NORMALS |
                                     Sphere.GENERATE_TEXTURE_COORDS,
                                     moonPoly);
      moon.setAppearance(app);
      lTrans.addChild(moon);

      // Задаём положение луны
      yAxis = new Transform3D();
      rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,0,0,moonSteps, 0, 0,0, 0, 0);
      rotator =  new RotationInterpolator(rotationAlpha,lRotTrans,yAxis,
                                           0.0f, (float) Math.PI*moonSpeed);
      rotator.setSchedulingBounds(bounds);
      lRotTrans.addChild(rotator);

      return objRoot;
    }

    // Создание источников света
    private void createLights(BranchGroup graphRoot)
    {
     // Создаём границы влияния источников света
     BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

     // Задаём глобальне освещение
     Color3f alColor = new Color3f(glcr,glcg,glcb);
     AmbientLight aLgt = new AmbientLight(alColor);
     aLgt.setInfluencingBounds(bounds);
     graphRoot.addChild(aLgt);

     // Задаём напрвленный, бесконечный источник света
     Color3f lColor1 = new Color3f(dlcr,dlcg,dlcb);
     Vector3f lDir1  = new Vector3f(1.0f, 1.0f, -1.0f);
     DirectionalLight lgt1 = new DirectionalLight(lColor1, lDir1);
     lgt1.setInfluencingBounds(bounds);
     graphRoot.addChild(lgt1);
    }

    // Считывание параметров из конфигурационного файла
    private void setParams() throws Exception
    {
      // массив строк параметров
      String[] params = new String[19];
      confPath = getCodeBase().toString().substring(6)+"config.txt";
      BufferedReader br = new BufferedReader(new FileReader(confPath));
      String str;
      int count=0;
      // считываем параметры в массив
      while ((str=br.readLine())!=null)
      {
        if (str.startsWith(";")) {continue;};
        if (str.indexOf("=")==-1) {continue;};
        params[count++]=str.substring(str.indexOf("=")+1).trim();
      }
      // задаём считанные значения
      etexture = new java.net.URL(getCodeBase().toString() + params[0]);
      mtexture = new java.net.URL(getCodeBase().toString() + params[1]);
      bgImage = new java.net.URL(getCodeBase().toString()  + params[2]);
      initScale = Float.parseFloat(params[3]);
      bgPoly = Integer.parseInt(params[4]);
      earthSize = Float.parseFloat(params[5]);
      earthPoly = Integer.parseInt(params[6]);
      earthSteps = Integer.parseInt(params[7]);
      earthSpeed = Float.parseFloat(params[8]);
      moonSize = Float.parseFloat(params[9]);
      moonPoly = Integer.parseInt(params[10]);
      moonSteps = Integer.parseInt(params[11]);
      moonSpeed = Float.parseFloat(params[12]);
      glcr = Float.parseFloat(params[13]);
      glcg = Float.parseFloat(params[14]);
      glcb = Float.parseFloat(params[15]);
      dlcr = Float.parseFloat(params[16]);
      dlcg = Float.parseFloat(params[17]);
      dlcb = Float.parseFloat(params[18]);
    }
}