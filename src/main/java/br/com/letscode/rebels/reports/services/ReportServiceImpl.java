package br.com.letscode.rebels.reports.services;

import br.com.letscode.rebels.core.util.Utils;
import br.com.letscode.rebels.item.domain.dto.ItemDTO;
import br.com.letscode.rebels.item.services.ItemService;
import br.com.letscode.rebels.person.domain.dto.PersonDisplayDTO;
import br.com.letscode.rebels.person.services.PersonService;
import br.com.letscode.rebels.reports.domain.dto.ReportMeanResourcesDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalPointsDTO;
import br.com.letscode.rebels.reports.domain.dto.TotalItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private PersonService personService;

    @Autowired
    private ItemService itemService;

    @Override
    public ReportTotalDTO getTotal(){


        int totalPersons = personService.countAllBy();
        float percentageOfRebels = 0;
        float totalTraitors = 0;

        if(totalPersons != 0){
            int totalRebels = personService.countAllByRebelIsTrue();
            percentageOfRebels = Utils.calculatePercentage(totalPersons,totalRebels);
            totalTraitors = 100 - percentageOfRebels;
        }

        return ReportTotalDTO.builder()
                .totalOfPersons(totalPersons)
                .totalRebels(percentageOfRebels+"%")
                .totalTraitors(totalTraitors+"%")
                .build();
    }

    @Override
    public ReportMeanResourcesDTO getReportMean(){

        List<PersonDisplayDTO> rebelds = this.personService.findAll();
        int totalRebels = this.personService.countAllByRebelIsTrue();
        List<ItemDTO> items = this.itemService.findAll();

        Map<Long, Integer> map = new HashMap<>();
        Map<Long, String> mapName = new HashMap<>();

        this.prepareMaps(items, map, mapName);
        this.sumAllResources(rebelds, map);


        return ReportMeanResourcesDTO.builder().items(map.keySet().stream().map(s -> {
            int total = map.get(s);
            return new TotalItemDTO(mapName.get(s), ""+(total == 0 ? total : (float)total / (float)totalRebels));
        }).collect(Collectors.toList())).build();

    }

    private void sumAllResources(List<PersonDisplayDTO> rebelds, Map<Long, Integer> map) {
        rebelds.stream().forEach(rebeld -> rebeld.getInventory().stream().forEach(inventoryItemDTO -> {
            Integer quantity = map.get(inventoryItemDTO.getItemId());
            quantity += inventoryItemDTO.getQuantity();
            map.put(inventoryItemDTO.getItemId(),quantity);
        }));
    }

    private void prepareMaps(List<ItemDTO> items, Map<Long, Integer> map, Map<Long, String> mapName) {
        items.stream().forEach(itemDTO -> {
            map.put(itemDTO.getId(),0);
            mapName.put(itemDTO.getId(),itemDTO.getName());
        });
    }

    @Override
    public ReportTotalPointsDTO getLostPoints(){

        List<PersonDisplayDTO> rebelds = this.personService.findAllTraitors();
        AtomicReference<Integer> quantity = new AtomicReference<>(0);
        rebelds.stream().forEach(rebeld -> rebeld.getInventory().stream().forEach(inventoryItemDTO -> {
            quantity.updateAndGet(v -> v + inventoryItemDTO.getQuantity() * this.itemService.findById(inventoryItemDTO.getItemId()).getPoints());
        }));

        return ReportTotalPointsDTO.builder().quantity(quantity.get()).build();
    }
}
