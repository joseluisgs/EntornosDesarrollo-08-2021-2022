package es.joseluisgs.dam.controllers;

import es.joseluisgs.dam.models.Acuerdo;
import es.joseluisgs.dam.models.Backup;
import es.joseluisgs.dam.models.Pais;
import es.joseluisgs.dam.repositories.acuerdos.IAcuerdoRepository;
import es.joseluisgs.dam.repositories.paises.IPaisRepository;
import es.joseluisgs.dam.services.Storage.IStorageBackup;

import java.sql.SQLException;

public class BackupManager {
    private final IPaisRepository paisRepository;
    private final IAcuerdoRepository acuerdoRepository;
    private final IStorageBackup storageBackup;

    public BackupManager(IPaisRepository paisRepository, IAcuerdoRepository acuerdoRepository, IStorageBackup storageBackup) {
        this.paisRepository = paisRepository;
        this.acuerdoRepository = acuerdoRepository;
        this.storageBackup = storageBackup;
    }


    /**
     * Importa los datos desde un fichero de backup
     */
    public void importarDatos() throws SQLException {
        System.out.println("Importando datos de Backup: " + storageBackup.getStoragePath());
        var backup = storageBackup.load();
        System.out.println("Importando Paises...");
        if (backup.getPaises().size() > 0) {
            paisRepository.clearAll();
            for (Pais pais : backup.getPaises()) {
                paisRepository.save(pais);
            }
            System.out.println("Paises importados con éxito al repositorio: " + backup.getPaises().size() + " paises");
        } else {
            System.out.println("Ha existido un problema al importar los datos de Paises");
        }
        System.out.println("Importando Acuerdos...");
        if (backup.getAcuerdos().size() > 0) {
            acuerdoRepository.clearAll();
            for (Acuerdo acuerdo : backup.getAcuerdos()) {
                acuerdoRepository.save(acuerdo);
            }
            System.out.println("Acuerdos importados con éxito al repositorio: " + backup.getAcuerdos().size() + " acuerdos");
        } else {
            System.out.println("Ha existido un problema al importar los datos de Acuerdos");
        }
    }


    /**
     * Exporta los datos desde un fichero de Backup
     */
    public void exportarDatos() throws SQLException {
        System.out.println("Exportando datos a fichero de Backup...");
        var paises = paisRepository.findAll();
        var acuerdos = acuerdoRepository.findAll();
        Backup backup = Backup.builder()
                .paises(paises)
                .acuerdos(acuerdos)
                .build();
        var res = storageBackup.save(backup);
        if (res) {
            System.out.println("Exportando " + backup.getPaises().size() + " paises");
            System.out.println("Exportando " + backup.getAcuerdos().size() + " acuerdos");
            System.out.println("Datos exportados con éxito en: " + storageBackup.getStoragePath());
        } else {
            System.out.println("Ha existido un problema al exportar los datos");
        }
    }
}
