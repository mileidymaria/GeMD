package br.com.Gerenciador.MD.Services;

import br.com.Gerenciador.MD.Gerenciamento.*;
import br.com.Gerenciador.MD.Repository.*;
import br.com.Gerenciador.MD.Exception.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {

    @Autowired
    private DownloadRepository repo;

    public List<Download> findAll() {
        return repo.findAll();
    }
    
    
    public List<Download> findAll(Long DownloadId) {
        return repo.findByDownloadById(DownloadId);
    }

    public List<Download> findAll(int page, int size, Long DownloadId) {
        Pageable p = PageRequest.of(page, size);
        if ( DownloadId!= 0) {
            return repo.findAll();
        }

        return repo.findAll(p).toList();
    }

    public Download findById(Long id) {
        Optional<Download> obj = repo.findById(id);
        if (obj.isEmpty()) {
            throw new NotFoundException("Download não encontrado.");
        }
        return obj.get();
    }    
    
    public Download save(Download bm) {
        
        
        try {
            return repo.save(bm);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            System.out.println("\n erro " + e );
            throw new RuntimeException("Falha ao registrar o download.");
        }
    }

    
    public Download update(Download bm) {
                
        //verificar se o material já existe
        Download obj = findById(bm.getId());
        try {
            return repo.save(bm);
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
                if (t instanceof ConstraintViolationException) {
                    throw ((ConstraintViolationException) t);
                }
            }
            throw new RuntimeException("Falha ao atualizar os dados do download");
        }
    }

    public void delete(Long id) {
        Download obj = findById(id);
        verificaExclusaoDowndloasFuturos(obj);
        try {
            repo.delete(obj);           
            
        } catch (Exception e) {
            throw new RuntimeException("Falha ao deletar o download.");
        }
    }
    
    
    public void verificaExclusaoDowndloasFuturos(Download bm) {
    	
    	//se a data atual, do sistema, for antes da data do download, não vai poder ser excluído
    	
    	//pega a data atual do sistema
    	Date d = new Date();
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(d);
    	
    	if (cal.before(bm.getData())) {
            throw new RuntimeException("Download futuroo. Exclusão não permitida.");
    	}
    	
    }     

}
