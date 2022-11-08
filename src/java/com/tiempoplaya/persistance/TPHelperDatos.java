/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.persistance;

import com.github.fluent.hibernate.internal.transformer.FluentHibernateResultTransformer;
import com.tiempoplaya.model.TDatos;
import com.tiempoplaya.model.TFotografias;
import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TPlayasTopFive;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.utils.CoordenadasUtils;
import com.tiempoplaya.utils.RatingDataPlayas;
import com.tiempoplaya.utils.TDatosAverage;
import com.tiempoplaya.utils.TDatosReport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

/**
 *
 * @author jpenaab
 */
public class TPHelperDatos implements TPInterfDAODato {

    @Override
    public Boolean addNewData(int idUser, int idPlaya, int idFoto, int viento, int mar, int tiempo, int ocupacion, int agua, int arena, int medusas, int bandera, String utmx, String utmy, String utmz) {

        Boolean resultado = false;

        TUsuarios usuario = new TUsuarios();
        usuario = new TPHelperUsuarios().getUsuarioByID(idUser);

        TPlayas playa = new TPlayas();
        playa = new TPHelperPlayas().getPlayaById(idPlaya);

        //distancia permitida para votar
        //600 metros, 0.6
        Double distancia = CoordenadasUtils.getModuloVectorExtremos(playa.getDoubleCoordUTMx(), playa.getDoubleCoordUTMy(), playa.getIntCoordUTMz(), Double.valueOf(utmx), Double.valueOf(utmy), Integer.valueOf(utmz));

        TDatos dato;

        if (idFoto == -1) {

            dato = new TDatos(usuario, playa, null, utmx, utmy, utmz, viento, mar, tiempo, ocupacion, agua, arena, medusas, bandera, new Timestamp(System.currentTimeMillis()));

        } else {

            TFotografias fotografia = new TFotografias();
            fotografia = new TPHelperFotografia().getFotografiasById(idFoto);

            dato = new TDatos(usuario, playa, fotografia, utmx, utmy, utmz, viento, mar, tiempo, ocupacion, agua, arena, medusas, bandera, new Timestamp(System.currentTimeMillis()));

        }

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        if (distancia <= 2) {
            //2 kilometro
            //0.6, 600 metros
            try {

                transaction = session.beginTransaction();
                session.save(dato);
                transaction.commit();
                resultado = true;

            } catch (Exception e) {

                if (transaction != null) {
                    transaction.rollback();
                }

                e.printStackTrace();
                throw e;

            } finally {
                session.close();
            }
        }

        return resultado;

    }

    @Override
    public TDatosAverage getPlayaStatusAverage(Integer idP) {

        List<TDatos> listTdatos = new ArrayList<TDatos>();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TDatos as datos where datos.TPlayas = '" + idP + "' and datos.timestamp > current_date");
            listTdatos = (List<TDatos>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        Integer viento = 0;
        Integer oleaje = 0;
        Integer nubosidad = 0;
        Integer ocupacion = 0;
        Integer limpiezaAgua = 0;
        Integer limpiezaArena = 0;
        Integer banderaMar = 0;
        Integer medusas = 0;

        TDatosAverage average = new TDatosAverage();

        if (listTdatos.size() > 0) {

            for (TDatos a : listTdatos) {

                viento = viento + a.getViento();
                oleaje = oleaje + a.getOleaje();
                nubosidad = nubosidad + a.getNubosidad();
                ocupacion = ocupacion + a.getOcupacion();
                limpiezaAgua = limpiezaAgua + a.getLimpiezaAgua();
                limpiezaArena = limpiezaArena + a.getLimpiezaArena();
                banderaMar = banderaMar + a.getBanderaMar();
                medusas = medusas + a.getMedusas();

            }

            viento = Math.round(viento / listTdatos.size());
            oleaje = Math.round(oleaje / listTdatos.size());
            nubosidad = Math.round(nubosidad / listTdatos.size());
            ocupacion = Math.round(ocupacion / listTdatos.size());
            limpiezaAgua = Math.round(limpiezaAgua / listTdatos.size());
            limpiezaArena = Math.round(limpiezaArena / listTdatos.size());
            banderaMar = Math.round(banderaMar / listTdatos.size());
            medusas = Math.round(medusas / listTdatos.size());

            average = new TDatosAverage(viento, oleaje, nubosidad, ocupacion, limpiezaAgua, limpiezaArena, medusas, banderaMar);

        } else {
            average.setEmptyData();
        }

        System.out.println(listTdatos.size() + " - " + average);

        return average;

    }

    @Override
    public List<TPlayasTopFive> getTopFivePlayas() {

        List<TPlayasTopFive> topPlayas = new ArrayList<TPlayasTopFive>();

        List<RatingDataPlayas> list;

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            String sql = "select count(*) as numberDataRating, id_playa as playaIdentifier from tiempoplaya.t_datos group by id_playa order by numberDataRating desc";
            Query q = session.createSQLQuery(sql)
                    .addScalar("numberDataRating", StandardBasicTypes.INTEGER)
                    .addScalar("playaIdentifier", StandardBasicTypes.INTEGER);
            //Query q = session.createQuery("SELECT count(*), d1.TPlayas.id from TDatos as d1 group by d1.TPlayas.id order by col_0_0_ desc");
            //adaptar a una clase que esta asociada con un POJO o Entity
            q.setResultTransformer(Transformers.aliasToBean(RatingDataPlayas.class));
            //Query q = session.createQuery("SELECT DISTINCT d.TPlayas FROM TDatos AS d");
            q.setFirstResult(0);
            q.setMaxResults(5); //top 5

            list = q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        for (RatingDataPlayas p : list) {

            TPlayas p1 = new TPHelperPlayas().getPlayaById(p.getPlayaidentifier());
            TPlayasTopFive tPlayasTopFive = new TPlayasTopFive(p1, p.getNumberdatarating());
            topPlayas.add(tPlayasTopFive);
        }

        return topPlayas;
    }

    @Override
    public List<TPlayas> getFavouritesPlayas(Integer userId) {

        List<TPlayas> favPlayas = new ArrayList<TPlayas>();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("SELECT DISTINCT d.TPlayas FROM TDatos AS d WHERE d.TUsuarios = " + userId + "");
            q.setFirstResult(0);
            q.setMaxResults(6); //top 6
            favPlayas = (List<TPlayas>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        return favPlayas;
    }

    @Override
    public List<TDatosReport> getInfoSended(Integer userId) {

        DateFormat DF = new SimpleDateFormat("YYYY-MM-dd HH:mm");

        List<TDatosReport> dataListReport = new ArrayList<TDatosReport>();
        List<TDatos> dataList = new ArrayList<TDatos>();

        Transaction transaction = null;
        Session session = TPHibernateUtil.getSession();

        try {
            transaction = session.beginTransaction();
            Query q = session.createQuery("from TDatos as datos where datos.TUsuarios.id = " + userId + " ORDER BY datos.timestamp DESC");
            q.setFirstResult(0);
            q.setMaxResults(15);
            dataList = (List<TDatos>) q.list();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
            throw e;

        } finally {
            session.close();
        }

        for (TDatos o : dataList) {

            String nombrePlaya = o.getTPlayas().getNombre();
            Date dt = o.getTimestamp();
            String timestamp = DF.format(dt);
            TDatosReport r = new TDatosReport(o.getId(), nombrePlaya, o.getCoordUTMx(), o.getCoordUTMy(), o.getCoordUTMz(), o.getViento(), o.getOleaje(), o.getNubosidad(), o.getOcupacion(), o.getLimpiezaAgua(), o.getLimpiezaArena(), o.getMedusas(), o.getBanderaMar(), timestamp);

            dataListReport.add(r);
        }

        return dataListReport;
    }

}
