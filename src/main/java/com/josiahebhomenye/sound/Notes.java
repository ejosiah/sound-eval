package com.josiahebhomenye.sound;

/**
 * E0	20.60	1674.62
 * F0	21.83	1580.63
 *  F#0/Gb0 	23.12	1491.91
 * G0	24.50	1408.18
 *  G#0/Ab0 	25.96	1329.14
 * A0	27.50	1254.55
 */
public enum Notes {
    C_0(16.35),
    C_0_SHARP(17.2),
    D_0_FLAT(17.2),
    D_0(18.35),
    D_0_SHARP(19.45),
    E_0_FLAT(19.45),
    E_0(20.60),
    F_0(21.83),
    F_0_SHARP(23.12),
    G_O_FLAT(23.12),
    G_0(24.50),
    G_0_SHART(25.96),
    A_0_FLAT(25.96),
    A_0(27.50),
    A_0_SHARP(29.14),
    B_0_FLAT(29.14),
    B_0(30.87),
    C_1(32.70),
    C_1_SHARP(34.65),
    D_1_FLAT(34.65),
    D_1(36.71),
    D_1_SHARP(38.89),
    E_1_FLAT(38.89),
    E_1(41.20),
    F_1(43.65),
    F_1_SHARP(46.25),
    G_1_FLAT(46.25),
    G_1(49.00),
    G_1_SHARP(51.91),
    A_1_FLAT(51.91),
    A_1(55.00),
    A_1_SHARP(58.27),
    B_1_FLAT(58.27),
    B_1(61.74),
    C_2(65.41),
    C_2_SHARP(69.30),
    D_2_FLAT(69.30),
    D_2(73.42),
    D_2_SHARP(77.78),
    E_2_FLAT(77.78),
    E_2(82.41),
    F_2(87.31),
    F_2_SHARP(92.50),
    G_2_FLAT(92.50),
    G_2(98.00),
    G_2_SHART(103.83),
    A_2_FLAT(103.83),
    A_2(110.00),
    A_2_SHARP(116.54),
    B_2_FLAT(116.54),
    B_2(123.47);

    private Notes(double value){
        this.value = value;
    }

    private double value;

    public double getValue(){
        return value;
    }
}
