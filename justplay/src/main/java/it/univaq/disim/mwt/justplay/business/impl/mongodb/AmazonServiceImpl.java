package it.univaq.disim.mwt.justplay.business.impl.mongodb;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AmazonServiceImpl implements AmazonService {

    @Autowired
    private AmazonRepository amazonRepository;

    @Override
    public Amazon findByidVideogioco(Long idVideogioco) throws BusinessException{
        try {
            
        }catch (Exception e) {
            throw new BusinessException(e);
        }
    }

}