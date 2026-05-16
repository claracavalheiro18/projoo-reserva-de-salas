package com.reservasalas.visitor;

public class RecurrenceRule {
    private final Frequency frequency;
    private final int interval;
    private final int ocurrences;

    public RecurrenceRule(Frequency frequency, int interval, int ocurrences) {
        this.frequency = frequency;
        this.interval = interval;
        this.ocurrences = ocurrences;
    }

    public Frequency getFrequency() { return frequency; }
    public int getInterval()        { return interval; }
    public int getOcurrences()      { return ocurrences; }

    @Override
    public String toString() {
        return String.format("RecurrenceRule[Frequency:%s, Interval:%d, Ocurrences:%d]",
                frequency, interval, ocurrences);
    }
}
