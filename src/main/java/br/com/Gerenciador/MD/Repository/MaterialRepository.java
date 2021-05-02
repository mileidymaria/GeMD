package br.com.Gerenciador.MD.Repository;

import br.com.Gerenciador.MD.Gerenciamento.Material;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository <Material, Long>{
	
    @Query("SELECT m FROM Material m WHERE m.id = :id")
    public List<Material> findByMaterialById(@Param("id") Long id);
    
    @Query("SELECT m FROM Material m WHERE m.preco = :preco")
    public List<Material> findByMaterialByPreco(@Param("preco") Double preco);
}
