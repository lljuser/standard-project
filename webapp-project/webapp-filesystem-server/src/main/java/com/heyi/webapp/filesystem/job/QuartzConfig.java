package com.heyi.webapp.filesystem.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(com.heyi.webapp.filesystem.job.TransactionPackJob.TransactionPackJobConfig.class)
public class QuartzConfig {

}
