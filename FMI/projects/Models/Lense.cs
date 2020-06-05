using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PhotoStudio.Models
{
    public class Lense
    {
       //  public string lense_name { set; get; } (attributes (all optional): byte lens_angle)
        public string lens_image { set; get; }
        public string lens_type { set; get; }
        public string lens_apperature { set; get; }
        public string lens_manifacturer { set; get; }
        public string lens_focus_range { set; get; }

        // float lens_price (attribiutes (all optional): string currency)

    }
}