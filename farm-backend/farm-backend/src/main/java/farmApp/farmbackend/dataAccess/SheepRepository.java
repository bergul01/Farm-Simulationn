package farmApp.farmbackend.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import farmApp.farmbackend.entities.Sheep;

public interface SheepRepository extends JpaRepository<Sheep, Long>{
	

}
