package org.freekode.cryptobot.platformbinance

import org.springframework.context.annotation.Configuration


@Configuration
class QuartzConfiguration {
//    @Bean
//    fun jobADetails(): JobDetail {
//        return JobBuilder.newJob(JobOne::class.java)
//            .withIdentity(JobKey("sampleJobA"))
//            .storeDurably()
//            .build()
//    }

//    @Bean
//    fun jobATrigger(jobADetails: JobDetail?): Trigger {
//        return TriggerBuilder.newTrigger()
//            .forJob(jobADetails)
//            .withIdentity("TriggerA")
//            .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * ? * * *"))
//            .build()
//    }

//    @Bean
//    fun jobBDetails(): JobDetail {
//        return JobBuilder.newJob(JobTwo::class.java).withIdentity("sampleJobB").storeDurably().build()
//    }

//    @Bean
//    fun jobBTrigger(jobBDetails: JobDetail?): Trigger {
//        val jobDataMap = JobDataMap()
//        jobDataMap["somedata"] = UUID.randomUUID().toString()
//        return TriggerBuilder.newTrigger()
//            .forJob(jobBDetails)
//            .withIdentity("TriggerB")
//            .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * ? * * *"))
//            .usingJobData(jobDataMap)
//            .build()
//    }
}
