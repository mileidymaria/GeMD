package br.com.Gerenciador.MD.Repository;

import br.com.Gerenciador.MD.Gerenciamento.*;

import java.util.List;
import java.util.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DownloadRepository extends JpaRepository <Download, Long>{
	
    @Query("SELECT bm FROM Download bm WHERE bm.id = :id")
    public List<Download> findByDownloadById(@Param("id") Long id);
    
    @Query("SELECT bm FROM Download bm WHERE bm.data = :data")
    public List<Download> findDownloadNaData(@Param("data") Calendar data);
    
    @Query("SELECT bm FROM Download bm WHERE bm.material = :material") /* encontra os downloads com o id 
    da classe material referente a uma modaldiade*/
    public List<Download> findDownloadPeloIdentificadorDoMaterial(@Param("material") Long material);    
}