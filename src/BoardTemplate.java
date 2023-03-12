public class BoardTemplate {
            /*
                Template:
                ---------
                {2, 2, 2, 2, 2, 2, 2, 2},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 3, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 0},
                {2, 1, 1, 0, 0, 0, 1, 1},
                {2, 1, 1, 0, 0, 0, 1, 1}

                0 = PEG
                1 = off limits
                2 = ruler
                3 = empty
                4 = player


                CLI Result:
                -----------
                    a  b  c  d  e  f  g
                 1        o  o  o
                 2        o  o  o
                 3  o  o  o  o  o  o  o
                 4  o  o  o  .  o  o  o
                 5  o  o  o  o  o  o  o
                 6        o  o  o
                 7        o  o  o
            };*/

    static HoleStatus[][] english_cross = {
            /*
            // ** LVL 2
            // English cross (33 holes)

                    a  b  c  d  e  f  g
                 1        o  o  o
                 2        o  o  o
                 3  o  o  o  o  o  o  o
                 4  o  o  o  .  o  o  o
                 5  o  o  o  o  o  o  o
                 6        o  o  o
                 7        o  o  o
            */
            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.VACANT, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };

    static HoleStatus[][] french = {
            /*
            // ** LVL 2
            // 17 century French style (37 holes)

                        a  b  c  d  e  f  g
                     1        o  o  o
                     2     o  o  o  o  o
                     3  o  o  o  o  o  o  o
                     4  o  o  o  .  o  o  o
                     5  o  o  o  o  o  o  o
                     6     o  o  o  o  o
                     7        o  o  o
            */

            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.VACANT, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };

    static HoleStatus[][] asymmetrical = {
            /*
            // ** LVL 3
            // George Bell 20th century Asymmetrical (39 holes)

                       a  b  c  d  e  f  g  h
                    1        o  o  o
                    2        o  o  o
                    3        o  o  o
                    4  o  o  o  o  o  o  o  o
                    5  o  o  o  .  o  o  o  o
                    6  o  o  o  o  o  o  o  o
                    7        o  o  o
                    8        o  o  o
            */

            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.VACANT, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };

    static HoleStatus[][] diamond = {
            /*
            // ** LVL 4
            // Diamond (41 holes)

                       a  b  c  d  e  f  g  h  i
                    1              o
                    2           o  o  o
                    3        o  o  o  o  o
                    4     o  o  o  o  o  o  o
                    5  o  o  o  o  .  o  o  o  o
                    6     o  o  o  o  o  o  o
                    7        o  o  o  o  o
                    8           o  o  o
                    9              o
            */

            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.VACANT, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}

    };

    static HoleStatus[][] german = {
            /*
            // ** LVL 5
            // German style (45 holes)

                       a  b  c  d  e  f  g  h  i
                    1           o  o  o
                    2           o  o  o
                    3           o  o  o
                    4  o  o  o  o  o  o  o  o  o
                    5  o  o  o  o  .  o  o  o  o
                    6  o  o  o  o  o  o  o  o  o
                    7           o  o  o
                    8           o  o  o
                    9           o  o  o
            */

            {HoleStatus.OFF_LIMITS, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER, HoleStatus.RULER},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.VACANT, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS},
            {HoleStatus.RULER, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.PEG, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS, HoleStatus.OFF_LIMITS}
    };
}
