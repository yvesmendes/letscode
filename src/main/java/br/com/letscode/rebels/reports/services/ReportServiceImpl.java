package br.com.letscode.rebels.reports.services;

import br.com.letscode.rebels.core.util.Utils;
import br.com.letscode.rebels.item.domain.dto.ItemDTO;
import br.com.letscode.rebels.item.services.ItemService;
import br.com.letscode.rebels.person.domain.dto.InventoryItemDTO;
import br.com.letscode.rebels.person.domain.dto.PersonDisplayDTO;
import br.com.letscode.rebels.person.services.PersonService;
import br.com.letscode.rebels.reports.domain.dto.ReportMeanResourcesDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalPointsDTO;
import br.com.letscode.rebels.reports.domain.dto.TotalItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{

    public static final String PERCENT_STRING = "%";
    public static final int TOTAL_AMOUNT_PERCENTAGE = 100;
    @Autowired
    private PersonService personService;

    @Autowired
    private ItemService itemService;

    @Override
    public ReportTotalDTO getTotal(){

        int totalPersons = personService.countAllBy();
        float percentageOfRebels = BigDecimal.ZERO.floatValue();
        float totalTraitors = BigDecimal.ZERO.floatValue();

        if(totalPersons != BigDecimal.ZERO.intValue()){
            int totalRebels = personService.countAllByRebelIsTrue();
            percentageOfRebels = Utils.calculatePercentage(totalPersons,totalRebels);
            totalTraitors = TOTAL_AMOUNT_PERCENTAGE - percentageOfRebels;
        }

        return ReportTotalDTO.builder()
                .totalOfPersons(totalPersons)
                .totalRebels(percentageOfRebels+ PERCENT_STRING)
                .totalTraitors(totalTraitors+PERCENT_STRING)
                .build();
    }

    @Override
    public ReportMeanResourcesDTO getReportMean(){

        List<PersonDisplayDTO> rebels = this.personService.findAll();
        int totalRebels = this.personService.countAllByRebelIsTrue();
        List<ItemDTO> items = this.itemService.findAll();

        Map<Long, Integer> map = new HashMap<>();
        Map<Long, String> mapName = new HashMap<>();

        this.prepareMaps(items, map, mapName);
        this.sumAllResources(rebels, map);

        return ReportMeanResourcesDTO.builder().items(map.keySet().stream().map(s -> {
            int total = map.get(s);
            return TotalItemDTO.builder()
                    .itemName(mapName.get(s))
                    .quantity(calculateTotalValue(totalRebels, total)).build();
        }).collect(Collectors.toList())).build();

    }

    private String calculateTotalValue(float totalRebels, int total) {
        return Float.toString(total == BigDecimal.ZERO.intValue() ? total : (float)total / totalRebels);
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
            map.put(itemDTO.getId(),BigDecimal.ZERO.intValue());
            mapName.put(itemDTO.getId(),itemDTO.getName());
        });
    }

    @Override
    public ReportTotalPointsDTO getLostPoints(){

        List<PersonDisplayDTO> rebels = this.personService.findAllTraitors();
        AtomicReference<Integer> quantity = new AtomicReference<>(BigDecimal.ZERO.intValue());
        rebels.stream().forEach(rebel -> rebel.getInventory().stream().forEach(inventoryItemDTO -> {
            quantity.updateAndGet(actualValue -> calculateTotalPoints(inventoryItemDTO, actualValue));
        }));

        return ReportTotalPointsDTO.builder().quantity(quantity.get()).build();
    }

    private int calculateTotalPoints(InventoryItemDTO inventoryItemDTO, Integer actualValue) {
        return actualValue + inventoryItemDTO.getQuantity() * this.itemService.findById(inventoryItemDTO.getItemId()).getPoints();
    }
}
