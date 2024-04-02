package farmApp.farmbackend.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import farmApp.farmbackend.entities.Chicken;

//JpaRepository hazır metotlar save, findById gibi
public interface ChickenRepository extends JpaRepository<Chicken, Long>{
	
	

}
