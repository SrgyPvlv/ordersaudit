package com.example.sergey.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sergey.Model.Prices;

@Repository
public interface PricesRepository extends JpaRepository<Prices,Long> {
	
	//поиск пунктов тцп по фильтрам в названии (и подрядчику) сортированные по номеру таблицы и номеру пункта
	@Query(value="select * from prices where contractor like (:contractor) and (lower(workname) like concat('%',:filter,'%') or lower(workname) like concat('%',:filter1,'%',:filter2,'%') or lower(workname) like concat('%',:filter2,'%',:filter1,'%')) order by tablenumber,ppnumber",nativeQuery=true)
	public List<Prices> findPriceItemsByFilter(@Param("contractor") String contractor, @Param("filter") String filter,@Param("filter1") String filter1,@Param("filter2") String filter2);

	//поиск пунктов тцп по номеру (и подрядчику)
	@Query(value="select * from prices where contractor like (:contractor) and lower(ppnumber) like (:pp)",nativeQuery=true)
	public List<Prices> findPriceItemByPpNumber(@Param("contractor") String contractor, @Param("pp") String pp);
	
	//поиск Всех пуктов тцп по подрядчику сортированные по номеру таблицы и номеру пункта
	public List<Prices> findByContractorOrderByTablenumberAscPpnumberAsc(String contractor);
	
	//удалить Все пункты тцп данного подрядчика
	public void deleteAllByContractor(String contractor);
	
	//поиск пунктов тцп по фильтрам в названии (по всем подрядчикам)
	@Query(value="select * from prices where lower(workname) like concat('%',:filter,'%') or lower(workname) like concat('%',:filter1,'%',:filter2,'%') or lower(workname) like concat('%',:filter2,'%',:filter1,'%') order by workname",nativeQuery=true)
	public List<Prices> findPriceItemsByFilterThroughAllContractors(@Param("filter") String filter,@Param("filter1") String filter1,@Param("filter2") String filter2);
}