using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PhotoStudio.Models
{
    public class Flash
    {
        //  public string flash_name { set; get; } (attributes (all optional): string flash_battery_type)
        public string flash_image { set; get; }
        public string flash_remote { set; get; }
        public int flash_main_number { set; get; }
        public string flash_manifacturer { set; get; }

        // float flash_price (attribiutes (all optional): string currency)
    }
}